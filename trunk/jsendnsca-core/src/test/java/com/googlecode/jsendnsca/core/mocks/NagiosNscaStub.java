package com.googlecode.jsendnsca.core.mocks;import java.io.ByteArrayInputStream;import java.io.DataInputStream;import java.io.DataOutputStream;import java.io.IOException;import java.io.InputStream;import java.net.ServerSocket;import java.net.Socket;import java.util.Date;import java.util.LinkedList;import java.util.List;import com.googlecode.jsendnsca.core.MessagePayload;import com.googlecode.jsendnsca.core.builders.MessagePayloadBuilder;import com.googlecode.jsendnsca.core.utils.IOUtils;public class NagiosNscaStub implements Runnable {	private List<MessagePayload> messagePayloads = new LinkedList<MessagePayload>();	private ServerSocket serverSocket;	private final int port;	private boolean listening;	private final String password;	private boolean sendInitialisationVector = true;	private int simulateTimeoutInMs = 0;	public NagiosNscaStub(int port, String password) {		this.port = port;		this.password = password;	}		public void setSendInitialisationVector(boolean sendInitialisationVector) {		this.sendInitialisationVector = sendInitialisationVector;	}		public void setSimulateTimeoutInMs(int simulateTimeoutInMs) {		this.simulateTimeoutInMs = simulateTimeoutInMs;	}	public void start() throws Exception {		serverSocket = new ServerSocket(port);		Thread listeningThread = new Thread(this);		listening = true;		listeningThread.start();		while(!serverSocket.isBound()) {			// wait for socket server to be bound before exiting		}	}	public void run() {		while (listening) {			try {				new MultiServerThread(serverSocket.accept()).start();			}			catch (IOException ignore) {			}		}	}	public List<MessagePayload> getMessagePayloadList() {		return messagePayloads;	}	public void clearMessagePayloadList() {		messagePayloads.clear();	}		public void stop() throws IOException {		listening = false;		serverSocket.close();	}		private class MultiServerThread extends Thread {		private static final int INITIALISATION_VECTOR_SIZE = 128;		private Socket socket = null;		public MultiServerThread(Socket socket) {			super("MultiServerThread");			this.socket = socket;		}		public void run() {			DataOutputStream outputStream = null;			InputStream inputStream = null;			try {				outputStream = new DataOutputStream(socket.getOutputStream());				try {					Thread.sleep(simulateTimeoutInMs);				} catch (InterruptedException ignored) {				}								byte[] initVector = new byte[INITIALISATION_VECTOR_SIZE];				if (sendInitialisationVector) {					outputStream.write(initVector);					outputStream.writeInt((int) new Date().getTime());					outputStream.flush();					inputStream = socket.getInputStream();					messagePayloads.add(parsePayload(inputStream, initVector));				}			}			catch (IOException e) {				e.printStackTrace();			}			finally {				IOUtils.closeQuietly(inputStream);				IOUtils.closeQuietly(outputStream);				if (socket != null) {					try {						socket.close();					}					catch (IOException ignore) {					}				}			}		}				@SuppressWarnings("static-access")		private MessagePayload parsePayload(InputStream inputStream, byte[] initVector) throws IOException {			DataInputStream stream = new DataInputStream(inputStream);			byte[] bytes = new byte[720];			stream.readFully(bytes);			decrypt(bytes, initVector);						stream = new DataInputStream(new ByteArrayInputStream(bytes));						stream.skip(12);			short level = stream.readShort();			byte[] hostNameBytes = new byte[64];			stream.read(hostNameBytes);			String hostName = new String(hostNameBytes).trim();			byte[] serviceNameBytes = new byte[128];			stream.read(serviceNameBytes);			String serviceName = new String(serviceNameBytes).trim();			byte[] messageBytes = new byte[512];			stream.readFully(messageBytes);			String message = new String(messageBytes).trim();			return new MessagePayloadBuilder()				.withHostname(hostName)				.withLevel(level)				.withServiceName(serviceName)				.withMessage(message)				.create();		}				private void decrypt(byte[] sendBuffer, byte[] initVector) {			if (password != null) {				byte[] myPasswordBytes = password.getBytes();								for (int y = 0, x = 0; y < sendBuffer.length; y++, x++) {					if (x >= myPasswordBytes.length) {						x = 0;					}					sendBuffer[y] ^= myPasswordBytes[x];				}			}			for (int y = 0, x = 0; y < sendBuffer.length; y++, x++) {				if (x >= INITIALISATION_VECTOR_SIZE) {					x = 0;				}				sendBuffer[y] ^= initVector[x];			}		}			}}