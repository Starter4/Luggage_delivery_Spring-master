
import requests
import urllib
import pandas as pd
from requests_html import HTML, HTMLSession
import re
import numpy as np


def parse_google_search(query, output, count_pages):
    def get_source(url):
        # Повертає вихідний код з ЮРЛ
        try:
            session = HTMLSession()
            response = session.get(url) # юрл адреса сторінки, яку отримуємо

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

            return response # хтмл сторінки
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

        # повертаэ результат пошуку за запитом у вигляді датасету

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

query = input("Enter your query - ")

result = parse_google_search(query=query, output="dictionary", count_pages =2)
print("count news - ", len(result))

# домени, які необхідно вилучити з кінцевого результату
smi_names = ["instagram.com", "wikipedia.org", "facebook.com", "twitter.com", ]

#функція фільтрації. проходить по результату та вилучає всі співпадіння
def clear_result(result,smi_names, query):
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
    print("cleared all results, that have domen in smi_names")
    return result, search_media

# теж саме що і clear_result() але на регулярних виразах
def cleared_faster(result,smi_names, query):
    search_media = {query: []}
    for i in range(len(result) - 1, -1, -1):
        for j in smi_names:
            if re.search(j, str(result[i].values())):
                search_media.append(result[i]["link"])
                result.remove(result[i])
                break
            else:
                continue
    print("cleared all results, that have domen in smi_names")
    return result, search_media

cleared_result, smi_from_result = clear_result(result, smi_names, query)

print("count news 2 - ", len(cleared_result))
print("cleared result - ", cleared_result)
print("all smi from result - ", smi_from_result)
print("count smi - ", len(smi_from_result[query]))
