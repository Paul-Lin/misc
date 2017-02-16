# coding:utf8
import nltk

from nltk.corpus import gutenberg

gutenberg.fileids()

def for_print():
    '''
    显示每个文本的三个统计量
    :return:
    '''
    for fileid in gutenberg.fileids():
        num_chars=len(gutenberg.raw(fileid))
        num_words=len(gutenberg.words(fileid))
        num_sents=len(gutenberg.sents(fileid))
        num_vocab=len(set([w.lower() for w in gutenberg.words(fileid)]))
        print int(num_chars/num_words),int(num_words/num_sents),int(num_words/num_vocab),fileid

def print_longest():
    macbeth_sentences=gutenberg.sents('shakespeare-macbeth.txt')
    # print macbeth_sentences
    # print macbeth_sentences[1037]
    longest_len=max([len(s) for s in macbeth_sentences])
    print [s for s in macbeth_sentences if len(s)==longest_len]

def print_private():
    from nltk.corpus import webtext
    for fileid in webtext.fileids():
        print fileid, webtext.raw(fileid)[:65]

def print_chatroom():
    from nltk.corpus import nps_chat
    chatroom = nps_chat.posts('10-19-20s_706posts.xml')
    print chatroom[123]

def print_brown():
    from nltk.corpus import brown
    print brown.categories()
    print brown.words(categories='news')
    print brown.words(fileids=['cg22'])
    print brown.sents(categories=['news','reviews'])
    news_text=brown.words(categories='news')
    fdist=nltk.FreqDist([w.lower() for w in news_text])
    modals=['can','could','may','might','must','will']
    for m in modals:
        print m+':',fdist[m]

def print_modals():
    from nltk.corpus import brown
    cfd=nltk.ConditionalFreqDist(
        (genre,word)
        for genre in brown.categories()
        for word in brown.words(categories=genre)
    )
    genres=['news','religion','hobbies','science_fiction','romance','humor']
    modals=['can','could','may','might','must','will']
    cfd.tabulate(conditions=genres,samples=modals)

def print_reuters():
    from nltk.corpus import reuters
    # print reuters.fileids()
    # print reuters.categories()
    print reuters.categories('training/9865')
    print reuters.categories(['training/9865','training/9880'])
    print reuters.fileids('barley')
    print reuters.fileids(['barely','corn'])

def print_inaugural():
    from nltk.corpus import inaugural
    cfd=nltk.ConditionalFreqDist(
        (target,file[:4])
        for fileid in inaugural.fileids()
        for w in inaugural.words(fileid)
        for target in ['america','citizen']
        if w.lower().startswith(target)
    )
    cfd.plot()

def print_esp():
    import nltk
    print nltk.corpus.cess_esp.words()

def print_udhr():
    from nltk.corpus import udhr
    languages=['Chickasaw','English','German_Deutsch']
    cfd=nltk.ConditionalFreqDist(
        (lang,len(word))
        for lang in languages
        for word in udhr.words(lang+'-Latin1')
    )
    cfd.plot(cumulative=True)

def print_stopword():
    from nltk.corpus import stopwords
    print stopwords.words('english')

def content_fraction(text):
    stopwords=nltk.corpus.stopwords.words('english')
    content=[w for w in text if w.lower() not in stopwords]
    return float(len(content))/len(text)

def print_pos():
    text=nltk.word_tokenize('And now for something completely different')
    print nltk.pos_tag(text)

def print_pos1():
    text=nltk.word_tokenize('They refuse to permit us to obtain the refuse permit')
    nltk.pos_tag(text)

def print_similar():
    text=nltk.Text(word.lower() for word in nltk.corpus.brown.words())
    text.similar('woman')
    text.similar('bought')
    text.similar('over')

def tag_token():
    tagged_token=nltk.tag.str2tuple('fly/NN')
    print tagged_token
    print tagged_token[0]
    print tagged_token[1]
    print nltk.corpus.brown.tagged_words()
    print nltk.corpus.conll2000.tagged_words()
    # print nltk.corpus.indian()

def tag_news():
    from nltk.corpus import brown
    brown_news_tagged = brown.tagged_words(categories='news')
    tag_fd=nltk.FreqDist(tag for(word,tag) in brown_news_tagged)
    word_tag_pairs=nltk.bigrams(brown_news_tagged)
    print list(nltk.FreqDist(a[1] for (a,b) in word_tag_pairs if b[1]=='NP'))


def find_tags(tag_prefix,tagged_text):
    cfd=nltk.ConditionalFreqDist((tag,word) for (word,tag) in tagged_text if tag.startswith(tag_prefix))
    return dict((tag,cfd[tag].keys()[:5]) for tag in cfd.conditions())

def find_tags1(tag_prefix,tagged_text):
    cfd=nltk.ConditionalFreqDist((tag,word) for (word,tag) in tagged_text if tag_prefix.startswith(tag_prefix))
    return dict((tag,cfd[tag].keys()[:5]) for tag in cfd.conditions())
tagdict=find_tags('NN',nltk.corpus.brown.tagged_words(categories='news'))
for tag in sorted(tagdict):
    print tag,tagdict[tag]