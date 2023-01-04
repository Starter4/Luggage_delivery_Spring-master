
import requests
import urllib
import pandas as pd
from requests_html import HTML, HTMLSession
import re
import numpy as np
import tweepy
import config
from bs4 import BeautifulSoup
import requests
from gnews import GNews

# query = input("Enter your query - ")


def parse_google(query):
    def parse_google_search(query, output, count_pages):
        def get_source(url):
            # Повертає вихідний код з ЮРЛ
            try:
                session = HTMLSession()
                response = session.get(url)  # юрл адреса сторінки, яку отримуємо

                if response.status_code == 200:
                    return response
                elif response.status_code == 429:
                    print('Error: Too many requests. Google has temporarily blocked you. Try again later.')
                    exit()
                else:
                    print('Error:' + response)
                    exit()
            except requests.exceptions.RequestException as e:
                print(e)

        def get_site_results(url: str):
            # повертає джерело пошуку - юрл
            try:
                query = urllib.parse.quote_plus(url)
                response = get_source("https://www.google.co.uk/search?q=site%3A" + query)

                return response  # хтмл сторінки
            except requests.exceptions.RequestException as e:
                print(e)

        def parse_site_results(response: str):
            # повертає кількість сторінок
            # response - хтмл сайту
            try:
                if response.html.find("#result-stats", first=True):

                    string = response.html.find("#result-stats", first=True).text
                    if string:
                        # Remove values in paretheses, i.e. (0.31 seconds)
                        string = re.sub(r'\([^)]*\)', '', string)

                        # Remove non-numeric characters
                        string = re.sub('[^0-9]', '', string)

                        return string
                    else:
                        return 0
            except requests.exceptions.RequestException as e:
                print(e)

        def count_indexed_pages(url: str):
            # отримує дані юрл, аналізує відповідь і повертає кількість сторінок
            # повертає - кількість сторінок
            response = get_site_results(url)
            return parse_site_results(response)

        def get_indexed_pages(urls: list):
            # повертає датасет, який містить юрл адреси та кількість сторінок
            data = []
            for site in urls:
                site_data = {'url': site, 'indexed_pages': count_indexed_pages(site)}
                data.append(site_data)
            df = pd.DataFrame.from_records(data)
            df = df.sort_values(by='indexed_pages')
            return df

        def get_results(query: str):
            # парсить запит, повертає хтмл
            query = urllib.parse.quote_plus(query)
            response = get_source("https://www.google.co.uk/search?q=" + query)

            return response

        def get_next_page(response, domain="google.co.uk"):
            # теж саме що get_results() але для наступної сторінки
            css_identifier_next = "#pnnext"
            next_page_url = response.html.find(css_identifier_next, first=True).attrs['href']
            next_page = "https://www." + domain + next_page_url

            return next_page

        def parse_search_results(response):
            # бере ідентифікатори div та повертає необхідні результати
            # Повертає list список результату пошуку
            css_identifier_result = ".tF2Cxc"  # The class of the div containing each result, i.e. <div class="tF2Cxc">
            css_identifier_title = "h3"  # The element containing the title, i.e. <h3 class="...
            css_identifier_link = ".yuRUbf a"  # The class of the div containing the anchor, i.e. <div class="yuRUbf"><a ...
            css_identifier_text = ".VwiC3b"  # The class of the parent element containing the snippet <span>
            css_identifier_bold = ".VwiC3b span em"  # The class of the element containing the snippet <span><em>

            try:
                results = response.html.find(css_identifier_result)

                output = []

                for result in results:

                    if result.find(css_identifier_text, first=True):
                        text = result.find(css_identifier_text, first=True).text
                    else:
                        text = ''

                    if result.find(css_identifier_title, first=True):
                        title = result.find(css_identifier_title, first=True).text
                    else:
                        title = ''

                    if result.find(css_identifier_link, first=True):
                        link = result.find(css_identifier_link, first=True).attrs['href']
                    else:
                        link = ''

                    # Extract bold text
                    if result.find(css_identifier_bold, first=True):
                        bold = result.find(css_identifier_bold, first=True).text.lower()
                    else:
                        bold = ''

                    item = {
                        'title': title,
                        'link': link,
                        'text': text,
                        'bold': bold,
                    }

                    output.append(item)

                return output
            except requests.exceptions.RequestException as e:
                print(e)

        def get_serps(query: str,
                      output="dataframe",
                      pages=1,
                      domain="google.co.uk"):

            # повертає результат пошуку за запитом у вигляді датасету

            response = get_results(query)
            results = parse_search_results(response)
            next_page = get_next_page(response)

            page = 1
            while page <= pages:
                if page > 1:
                    response = get_source(next_page)
                    results = results + parse_search_results(response)
                    next_page = get_next_page(response)
                page += 1

            if results:
                if output == "dataframe":
                    df = pd.DataFrame.from_records(results)
                    df.index = np.arange(1, len(df) + 1)
                    df.index.names = ['position']
                    return df.reset_index()
                else:
                    return results

        return get_serps(query=query, output="dictionary", pages=2)

    result = parse_google_search(query=query, output="dictionary", count_pages=2)

    # домени, які необхідно вилучити з кінцевого результату
    smi_names = ["instagram.com", "wikipedia.org", "facebook.com", "twitter.com", ]

    # функція фільтрації. проходить по результату та вилучає всі співпадіння
    def clear_result(result, smi_names, query):
        search_media = {query: []}
        for dictionary in result:
            link = dictionary['link']
            for domen in smi_names:
                if domen in link:
                    search_media[query].append(link)
                    break
        for link in search_media[query]:
            for dictionary in result:
                if link == dictionary['link']:
                    result.remove(dictionary)
                    break
        # print("cleared all results, that have domen in smi_names")
        return result, search_media

    # теж саме що і clear_result() але на регулярних виразах
    def cleared_faster(result, smi_names, query):
        search_media = {query: []}
        for i in range(len(result) - 1, -1, -1):
            for j in smi_names:
                if re.search(j, str(result[i].values())):
                    search_media.append(result[i]["link"])
                    result.remove(result[i])
                    break
                else:
                    continue
        # print("cleared all results, that have domen in smi_names")
        return result, search_media

    cleared_result, smi_from_result = clear_result(result, smi_names, query)
    # print("count news 2 - ", len(cleared_result))
    # print("cleared result - ", cleared_result)
    # print("all smi from result - ", smi_from_result)
    # print("count smi - ", len(smi_from_result[query]))

    google_res = {
        "query": query,
        "name_smi": "Google",
        "dict_query": cleared_result
    }

    return google_res

def parse_twitter(q):
    client = tweepy.Client(bearer_token=config.BEARER_TOKEN)
    query = f"{q} -is:retweet lang:uk"
    tweet_flags = ['created_at', 'lang', 'text', "public_metrics"]

    tweets = client.search_recent_tweets(query=query, max_results=10,
                                         tweet_fields=tweet_flags,
                                         user_fields=['name', 'username'],
                                         expansions=['geo.place_id', 'author_id'])
    users = {u['id']: u for u in tweets.includes['users']}
    response = {
        "query": q,
        "flags": tweet_flags,
        "tweets": []
    }
    for tweet in tweets.data:
        if users[tweet.author_id]:
            user = users[tweet.author_id]
            lang = tweet.lang
            text = tweet.text
            public = tweet.public_metrics
            date = tweet.created_at
            name = user.name
            tag = user.username
            tweet_data = {'username': name,
                          'tag': tag,
                          'text': text,
                          'date': date,
                          'lang': lang,
                          'retweets': public['retweet_count'],
                          "replies": public["reply_count"],
                          "likes": public["like_count"]}
            response['tweets'].append(tweet_data)
    return response

def parse_tg(query, n_posts):
    response = {
        'query': query,
        'content': []
    }

    tg_links = ["https://t.me/UAonlii", "https://t.me/u_now"] #collected from DB
    for channel_link in tg_links:
        URL = channel_link[0:12] + "/s/" + channel_link[13::] + "?q=" + query.replace(" ", "+")
        channel = requests.get(URL).text
        soup = BeautifulSoup(channel, 'lxml')
        tgpost = soup.find_all('div', class_='tgme_widget_message')

        info = {
            "channel_link": channel_link,
            "message": []
        }
        count = 0
        for content in tgpost:
            if count < n_posts:
                full_message = {}
                full_message['text'] = content.find('div', class_='tgme_widget_message_text').text
                full_message['views'] = content.find('span', class_='tgme_widget_message_views').text
                full_message['timestamp'] = content.find('time', class_='time').text

                if content.find('a', class_='tgme_widget_message_photo_wrap') != None:
                    link = str(content.find('a', class_='tgme_widget_message_photo_wrap'))
                    full_message['url_image'] = re.findall(r"https://cdn4.*.*.jpg", link)[0]
                elif 'url_image' in full_message:
                    full_message.pop('url_image')
                info['message'].append(full_message)
                count += 1
            else:
                break
        response['content'].append(info)
    # a = response['content'][0]
    # print(a)
    # print(len(a['message']))
    # b = response['content'][1]
    # print(b)
    # print(len(b['message']))
    return response

def parse_actual_news(country, period, max_results):
    google_news = GNews(language='uk', country=country, period=period, max_results=max_results)
    recent_news = google_news.get_top_news()

    response = []

    for news in recent_news:
        news_paper = {}

        news_paper['title'] = news['title']
        news_paper['text'] = news['description']
        news_paper['date'] = news['published date']
        news_paper['link'] = news['url']
        news_paper['author'] = news['publisher']['title']

        article = google_news.get_full_article(news['url'])
        images = []
        try:
            for image in article.images:
                if ".jpg" in image or '.png' in image:
                    images.append(image)
                    break
        except:
            images = [""]
        news_paper['image'] = images
        response.append(news_paper)
    return response

google_result = parse_google(query)
twitter_result = parse_twitter(query)
tg_result = parse_tg(query, 1)
actual_news = parse_actual_news(country="UA", period="12h", max_results=10)


