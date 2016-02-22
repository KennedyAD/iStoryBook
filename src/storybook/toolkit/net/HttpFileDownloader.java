package storybook.toolkit.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.activation.UnsupportedDataTypeException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * Télécharger un fichier depuis une url.
 *
 * @author Fobec 2013
 * @see http://www.fobec.com/java/1125/telecharger-fichier-depuis-une-url.html
 */
public class HttpFileDownloader {

	/**
	 * Override class SSL.
	 */
	private class MyHostnameVerifier implements HostnameVerifier {

		/* (non-Javadoc)
		 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String, javax.net.ssl.SSLSession)
		 */
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	/**
	 * Override class SSL.
	 */
	private class MyTrustManager implements X509TrustManager {

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
		 */
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
	
	/** The url path. */
	private String urlPath;
	
	/** The ua. */
	private String ua = null; // user Agent
	
	/** The responde code. */
	private int respondeCode = -1; // http://fr.wikipedia.org/wiki/Liste_des_codes_HTTP
	
	/** The header fields. */
	private Map headerFields; // entete
	
	/** The time_start. */
	private long time_start; // debut de connexion

	/** The time_connect. */
	private long time_connect; // debut de transfert

	/** The time_end. */
	private long time_end; // fin

	/**
	 * Constructeur.
	 *
	 * @param url the url
	 */
	public HttpFileDownloader(String url) {
		this.urlPath = url;
	}

	/**
	 * As list string.
	 *
	 * @param txSizeExpected the tx size expected
	 * @param txSizeDownloaded the tx size downloaded
	 * @return the array list
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws KeyManagementException the key management exception
	 */
	public ArrayList<String> asListString(JTextField txSizeExpected, JTextField txSizeDownloaded)
			throws IOException, NoSuchAlgorithmException, KeyManagementException {
		BufferedReader reader = null;
		ArrayList<String> list = new ArrayList<>();
		try {
			HttpURLConnection curl = open();
			// Seul le format texte est accepté
			// String contentType = curl.getContentType();
			// if (!contentType.startsWith("text/"))
			// throw new UnsupportedDataTypeException("Bad format, only text can
			// be loaded");

			int sizeExpect = curl.getContentLength();
			int sizeFile = 0;
			txSizeExpected.setText("" + sizeExpect);

			reader = new BufferedReader(new InputStreamReader(curl.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				list.add(line);
				sizeFile += line.length();
				txSizeDownloaded.setText("" + sizeFile);
			}
		} finally {
			if (reader != null)
				reader.close();
		}
		return list;
	}

	/**
	 * Charger le contenu au format texte.
	 *
	 * @param txSizeExpected the tx size expected
	 * @param txSizeDownloaded the tx size downloaded
	 * @return String
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws KeyManagementException the key management exception
	 */
	public String asString(JTextField txSizeExpected, JTextField txSizeDownloaded)
			throws IOException, NoSuchAlgorithmException, KeyManagementException {
		BufferedReader reader = null;
		String html = "";
		try {
			HttpURLConnection curl = open();
			// Seul le format texte est accepté
			String contentType = curl.getContentType();
			if (!contentType.startsWith("text/"))
				throw new UnsupportedDataTypeException("Bad format, only text can be loaded");

			int sizeExpect = curl.getContentLength();
			int sizeFile = 0;
			txSizeExpected.setText("" + sizeExpect);

			reader = new BufferedReader(new InputStreamReader(curl.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				sizeFile += line.length();
				txSizeDownloaded.setText("" + sizeFile);
			}
			html = sb.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
		return html;
	}

	/**
	 * Entete de la ressource retourné par le serveur.
	 *
	 * @return String
	 */
	public String getHeader() {
		StringBuilder sb = new StringBuilder();
		Set headers = this.headerFields.entrySet();
		for (Iterator i = headers.iterator(); i.hasNext();) {
			Map.Entry map = (Map.Entry) i.next();
			if (map.getKey() != null)
				sb.append(map.getKey()).append(" : ").append(map.getValue()).append("\\n");
		}
		return sb.toString();
	}

	/**
	 * Code réponse du serveur.
	 *
	 * @return int
	 * @see http://fr.wikipedia.org/wiki/Liste_des_codes_HTTP
	 */
	public int getResponseCode() {
		return this.respondeCode;
	}

	/**
	 * Temps de connexion.
	 *
	 * @return int milliseconde
	 */
	public long getTimeConnect() {
		long t = this.time_connect - this.time_start;
		return t;
	}

	/**
	 * Duree du téléchargment.
	 *
	 * @return int milliseconde
	 */
	public long getTimeDownload() {
		long t = this.time_end - this.time_connect;
		return t;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getURL() {
		return this.urlPath;
	}

	/**
	 * Ouvrir une connexion http ou https.
	 *
	 * @return HttpURLConnection
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws KeyManagementException the key management exception
	 * @throws MalformedURLException the malformed url exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private HttpURLConnection open()
			throws NoSuchAlgorithmException, KeyManagementException, MalformedURLException, IOException {
		this.time_start = System.currentTimeMillis();
		URL url;
		HttpURLConnection curl = null;

		url = new URL(this.urlPath);
		if (this.urlPath.startsWith("https")) {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new MyTrustManager() }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
			curl = (HttpsURLConnection) new URL(this.urlPath).openConnection();
		} else
			curl = (HttpURLConnection) url.openConnection();

		if (this.ua != null)
			curl.setRequestProperty("User-Agent", this.ua);
		curl.setRequestMethod("GET");
		curl.setReadTimeout(5000);
		curl.setInstanceFollowRedirects(true);

		this.respondeCode = curl.getResponseCode();
		this.headerFields = curl.getHeaderFields();

		if (this.respondeCode != 200)
			// Redirection sur les 301 + 302 + 303
			if (this.respondeCode > 300 && this.respondeCode < 304) {
				this.urlPath = curl.getHeaderField("Location");
				return this.open();
			} else
				throw new MalformedURLException("wrong answer on URL " + this.urlPath);
		this.time_connect = System.currentTimeMillis();
		return curl;
	}

	/**
	 * Enregistrer fichier, nom en local différent.
	 *
	 * @param destFilename            String nom du fichier local
	 * @param txSizeExpected the tx size expected
	 * @param txSizeDownloaded the tx size downloaded
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws KeyManagementException the key management exception
	 */
	public void savetoFile(String destFilename, JTextField txSizeExpected, JTextField txSizeDownloaded)
			throws IOException, NoSuchAlgorithmException, KeyManagementException {
		RandomAccessFile outFile = null;
		InputStream inStream = null;
		byte[] buffer = new byte[4096];
		int bufferLength = -1;

		HttpURLConnection curl = open();
		int sizeExpect = curl.getContentLength();
		int sizeFile = 0;

		txSizeExpected.setText("" + sizeExpect);

		try {
			outFile = new RandomAccessFile(destFilename, "rw");
			inStream = curl.getInputStream();
			while ((bufferLength = inStream.read(buffer)) != -1) {
				outFile.write(buffer, 0, bufferLength);
				sizeFile += bufferLength;
				txSizeDownloaded.setText("" + sizeFile);
			}
			this.time_end = System.currentTimeMillis();

			if (sizeExpect != -1 && sizeFile != sizeExpect) {
				this.respondeCode = 406;
				throw new IOException(
						"Partial Contents - Expect " + sizeExpect + " bytes; download " + sizeFile + " bytes");
			}
		} finally {
			outFile.close();
			inStream.close();
		}
	}

	/**
	 * Fixer le user Agent de la requete.
	 *
	 * @param useragent the new user agent
	 */
	public void setUserAgent(String useragent) {
		this.ua = useragent;
	}
}
