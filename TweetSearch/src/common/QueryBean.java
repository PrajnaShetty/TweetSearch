package common;


public class QueryBean {
	
	private String screenName;
	private String userName;
	private String tweetId;
	private String userLocation;
	private String allWords;
	private String exactPhrase;
	private String withoutWord;
	private String approxPhrase; 
	private String hashtag;
	
	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}


	
	public String getAllWords() {
		return allWords;
	}
	
	public void setAllWords(String allWords) {
		this.allWords = allWords;
	}
	
	public String getExactPhrase() {
		return exactPhrase;
	}
	
	public void setExactPhrase(String exactPhrase) {
		this.exactPhrase = exactPhrase;
	}
	
	public String getWithoutWord() {
		return withoutWord;
	}
	public void setWithoutWord(String withoutWord) {
		this.withoutWord = withoutWord;
	}
	public String getApproxPhrase() {
		return approxPhrase;
	}
	public void setApproxPhrase(String approxPhrase) {
		this.approxPhrase = approxPhrase;
	}
	
	public String toString(){
		return getAllWords()+getApproxPhrase()+getExactPhrase()+getWithoutWord();
	}
}
