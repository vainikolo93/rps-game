/**
 * 
 */
package com.kahweh.rps.remote;

/**
 * This class present a communicating message between two remote players.
 * 
 * The message format:
 * 
 * <message gamename="" version="" time="" length="" player-id="" action="" param-num="">
 *     <param sequence="0" type="int">
 *     1
 *     </param>
 *     <param sequence="1" type="string">
 *     haha
 *     </param>
 *     <param sequence="2" type="boolean">
 *     true
 *     </param>
 *         ......
 * </message>
 * 
 * @author michael
 *
 */
public class Message {

	//define the message action
	public static final int TEXT_MESSAGE = 0;
	public static final int BOARD_SIZE = 1;
	public static final int PLAYER_INFO = 2;
	public static final int SET_FLAG = 3;
	public static final int SET_TRAP = 4;
	public static final int MOVE = 5;
	public static final int TIMEOUT = 6;
	public static final int CONFLICT_DECISION = 7;
	public static final int CONCEDE = 8;
	public static final int QUIT = 9;

	private String gameName;
	private String gameVersion;
	private long time;
	private long length;
	private String playerId;
	private int action;

	public Message() {}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String actionToString(int act) {
		switch (act) {
		case TEXT_MESSAGE :
			return "text message";
		case BOARD_SIZE :
			return "board size";
		case PLAYER_INFO :
			return "player info";
		case SET_FLAG :
			return "set flag";
		case SET_TRAP :
			return "set trap";
		case MOVE :
			return "move";
		case TIMEOUT :
			return "timeout";
		case CONFLICT_DECISION :
			return "conflict decision";
		case CONCEDE :
			return "concede";
		case QUIT :
			return "quit";
		default :
			return "wrong action";
		}
	}
}
