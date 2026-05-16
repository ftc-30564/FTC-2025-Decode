package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

public class MorseCodePlayer {
    private List<LightDuration> lightSequence = new ArrayList<>();
    private final long UNIT_LENGTH = 150;

    private final long DOT_DURATION = 1*UNIT_LENGTH;
    private final long BETWEEN_DOT_DASH_DURATION = 1*UNIT_LENGTH;
    private final long BETWEEN_LETTER_DURATION = 3*UNIT_LENGTH;
    private final long BETWEEN_WORD_DURATION = 7*UNIT_LENGTH;
    private final long DASH_DURATION = 3*UNIT_LENGTH;

    private int currentSequence = 0;
    private ElapsedTime timer = new ElapsedTime();
    private boolean timerStarted = false;

    private class LightDuration {
        long ms = 0;
        boolean white = false;

        public LightDuration(long ms, boolean white) {
            this.ms = ms;
            this.white = white;
        }
    }

    public void addSequence(String morse) {
        for (int i = 0; i < morse.length(); i++) {
            char c = morse.charAt(i);

            if (c == '.') {
                lightSequence.add(new LightDuration(DOT_DURATION, true));
                lightSequence.add(new LightDuration(BETWEEN_DOT_DASH_DURATION, false));
            }
            else if (c == '-') {
                lightSequence.add(new LightDuration(DASH_DURATION, true));
                lightSequence.add(new LightDuration(BETWEEN_DOT_DASH_DURATION, false));
            }
            else if (c == ' ') {
                lightSequence.add(new LightDuration(BETWEEN_LETTER_DURATION, false));
            }
            else if (c == '/')
                lightSequence.add(new LightDuration(BETWEEN_WORD_DURATION, false));

        }
    }

    public boolean playSequence() {
        if (currentSequence >= lightSequence.size())
            return true;


        if (timerStarted) {
            if (timer.milliseconds() > lightSequence.get(currentSequence).ms) {
                currentSequence ++;
                timer.reset();
                return currentSequence >= lightSequence.size();

            }
            return false;
        }
        timer.reset();
        timerStarted = true;
        return false;
    }
}
