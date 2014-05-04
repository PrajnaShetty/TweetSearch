TweetSearch
===========

This project has 2 parts 
1) Builds an inverted index using hadoop map reduce and contains web pages to submit queries that query the
index to retrieve tweets matching the query. The indexes are ranked using tf-idf ranking.
2) Builds index using Lucene and contains web pages to submit queries that run against the Lucene index to 
retrieve matching tweets. This part uses Lucene's query APIs. 
