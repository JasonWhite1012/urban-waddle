package components.utility;

public class MusicPanel {
    public final Sound NEWGAME_MUSIC = new Sound("/assets/sounds/NewGameMusic.wav");
    public final Sound INGAME_MUSIC = new Sound("/assets/sounds/InGameMusic.wav");
    public final Sound BOSS_MUSIC = new Sound("/assets/sounds/BossMusic.wav");
    public final Sound WIN_MUSIC = new Sound("/assets/sounds/Victory.wav");

    //overworld.playInLoop();
    public MusicPanel() {

    }

    public void setLegal() {
        if ( NEWGAME_MUSIC.isOpen() ) NEWGAME_MUSIC.stop();
        if ( BOSS_MUSIC.isOpen() ) BOSS_MUSIC.stop();
        if ( WIN_MUSIC.isOpen() ) WIN_MUSIC.stop();
        if ( INGAME_MUSIC.isOpen() ) INGAME_MUSIC.stop();
    }

    public void setMusic(GameFlow GF) {
        setLegal();
        switch(GF){
            case GameFlow.NEW -> NEWGAME_MUSIC.playInLoop();
            case GameFlow.IN -> INGAME_MUSIC.playInLoop();
            case GameFlow.BOSS -> BOSS_MUSIC.playInLoop();
            case GameFlow.WIN -> WIN_MUSIC.play();
            default -> {}
        }
    }
}
