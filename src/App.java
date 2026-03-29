import java.util.InputMismatchException;

public class App {
    static final int REFRESH_RATE = 120;

    static final String bird = " >";
    static boolean dead = false;
    static int bird_pos = 3;

    static final String NEW_STACK = "               ";

    static final int COOLDOWN = 4;
    static int gravity_cooldown = COOLDOWN;

    static int player_score = 0;

    enum death_types {
        CEILING,
        FLOOR,
        PIPE
    }

    public static void main(String[] args) throws Exception {
        System.out.print("\033[2J\033[H"); // Puts debugger outside of view -> prevents rendering issues
        System.out.flush();

        KeyDetection input = new KeyDetection();
        int jump_ctr = input.get_jump();

        String line =  NEW_STACK;

        PipeClass initial = new PipeClass();
        String[] new_pipe = initial.get_pipe();

        PipeClass secondary = new PipeClass();
        String[] secondary_pipe = secondary.get_pipe();
        
        while (!dead)
        {
            if (jump_ctr < input.get_jump())
            {
                jump_ctr++;
                gravity_cooldown = COOLDOWN;
                bird_pos--;
            }
            render(new_pipe, secondary_pipe, line);

            line = line.substring(0, line.length()-1); //Shortens line, brings pipes to bird
            if (line.length() == 0) //Generates new pipe if closest pipe is out of view
            {
                initial = secondary;
                new_pipe = initial.get_pipe();
                line = NEW_STACK;

                secondary = new PipeClass();
                secondary_pipe = secondary.get_pipe();
            }

            if (gravity_cooldown == 0)
            {
                gravity_cooldown = COOLDOWN;
                bird_pos++;
            }
            else
            {
                gravity_cooldown--;
            }

            Thread.sleep(REFRESH_RATE);
        }
    
        System.out.println("YOU DIED \nScore: " + player_score);
        System.out.println("\n(Close Application To End Program)");
    }



    public static void dead_bird(death_types death)
    {
        int booster = -1;

        if (death == death_types.CEILING) {booster = 0; }
        else if (death == death_types.PIPE) { booster = 1; }
        else if (death == death_types.FLOOR) {booster = 2; }
        else
        {
            throw new InputMismatchException("UNEXPECTED BEHAVIOR: death_type enum called but has no proper match -> ?");
        }

        int height = PipeClass.get_height() + booster;
        String bird_relative_y = Integer.toString(height - bird_pos);

        System.out.print("\u001B[" + bird_relative_y  + "A");
        System.out.print("\u001B[31m" + " >" + "\u001B[0m");
        System.out.print("\r\u001B[" + bird_relative_y + "B");

        dead = true;
    }



    private static void render(String[] pipe, String[] secondary_pipe, String line)
    {
        String stack_display = "";
        String line_closer = " ".repeat( NEW_STACK.length() - line.length() );

        if (bird_pos < 0) //Checks if bird hit the top border
        {
            dead_bird(death_types.CEILING);
            return;
        }
        if(bird_pos > pipe.length - 1) //Checks if bird hit the bottom border
        {
            dead_bird(death_types.FLOOR);
            return;
        }
        if ("[]".equals( pipe[bird_pos] ) && line.length() == 1) //Checks if bird hit a pipewww
        {
            dead_bird(death_types.PIPE);
            return;
        }

        stack_display += "--------------------------------\n";
        for (int i = 0; i < pipe.length; i++)
        {
            if (i == bird_pos)
            {
                String modified_line = line.length() > 1 ? line.substring(0, line.length() - bird.length()) : "";
                
                if (line.length() == 1)
                {
                    stack_display += (bird + modified_line + pipe[i] + NEW_STACK.substring(0, NEW_STACK.length()-1) + secondary_pipe[i]) 
                        + line_closer + "\n";

                    player_score++;
                }
                else
                {
                    stack_display += (bird + modified_line + pipe[i] + NEW_STACK + secondary_pipe[i]) + 
                        line_closer + "\n";
                }
            }
            else
            {
                stack_display += (line + pipe[i] + NEW_STACK + secondary_pipe[i]) 
                    + line_closer + "\n";
            }
        }
        stack_display += "--------------------------------\n";
        System.out.print("\u001B[?25l" + "\033[H" + stack_display); //Hides cursor, overlaps old text, & prints next stack (prevents visual bugs)
    }
}