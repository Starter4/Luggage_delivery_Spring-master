
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

query = input("Enter your query - ")

limit = 50

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
                          'text': text[:limit]+"..." if len(text)>limit else text,
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
                text = content.find('div', class_='tgme_widget_message_text').text
                full_message['text'] = text[:limit]+"..." if len(text)>limit else text
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

def actual(country, period, max_results):
    google_news = GNews(language='uk', country=country, period=period, max_results=max_results)
    parse_news = google_news.get_top_news()
    return create_response(parse_news, google_news)

def by_qyery(query, country, period, max_results):
    google_news = GNews(language='uk', country=country, period=period, max_results=max_results)
    parse_news = google_news.get_news(query)
    return create_response(parse_news, google_news)

def create_response(parse_news, google_news):
    response = []
    for news in parse_news:
        news_paper = {}

        news_paper['title'] = news['title']
        news_paper['text'] = (
            news['description'][:limit] + "..." if len(news['description']) > limit else news['description'])

        news_paper['date'] = news['published date']
        news_paper['link'] = news['url']
        news_paper['news_resource'] = news['publisher']['title']
        news_paper['author_link'] = news['publisher']['href']
        article = google_news.get_full_article(news['url'])
        images = ''
        for i in range(1):
            try:
                for image in article.images:
                    if ".jpg" in image or '.png' in image:
                        images = image
            except:
                images = "https://www.ixbt.com/img/n1/news/2021/2/1/chrome-incognito-featured_large.jpg"
        news_paper['image'] = images
        print(news_paper)
        print('=====')
        response.append(news_paper)
    # print(response)
    print(len(response))
    return response

#test
google_result = by_qyery(query, country="UA", period="12h", max_results=150)
twitter_result = parse_twitter(query)
tg_result = parse_tg(query, 1)
actual_news = actual(country="UA", period="12h", max_results=150)
