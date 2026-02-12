public class App {
    static final String bird = " >";
    static boolean dead = false;
    static int bird_pos = 3;

    static final String NEW_STACK = "               ";

    static final int COOLDOWN = 2;
    static int gravity_cooldown = COOLDOWN;

    static int player_score = 0;

    public static void main(String[] args) throws Exception {
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
            print_stack(new_pipe, secondary_pipe, line);

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

            Thread.sleep(320);
            clear();
        }
    
        System.out.println("YOU DIED \nScore: " + player_score);
        System.out.println("\n(Close Application To End Program)");
    }



    private static void print_stack(String[] pipe, String[] secondary_pipe, String line)
    {
        if (bird_pos < 0 || bird_pos > pipe.length - 1) //Checks if bird hit the ground
        {
            dead = true;
            return;
        }
        if (pipe[bird_pos] == "[]" && line.length() == 1) //Checks if bird hit a pipe
        {
            dead = true;
            return;
        }

        System.out.println("--------------------------------");
        for (int i = 0; i < pipe.length; i++)
        {
            if (i == bird_pos)
            {
                String modified_line = line.length() > 1 ? line.substring(0, line.length() - bird.length()) : "";
                
                if (line.length() == 1)
                {
                    System.out.println(bird + modified_line + pipe[i] + NEW_STACK.substring(0, NEW_STACK.length()-1) + secondary_pipe[i]);
                    player_score++;
                }
                else
                {
                    System.out.println(bird + modified_line + pipe[i] + NEW_STACK + secondary_pipe[i]);
                }
            }
            else
            {
                System.out.println(line + pipe[i] + NEW_STACK + secondary_pipe[i]);
            }
        }
        System.out.println("--------------------------------");
    }


    private static void clear() //Hides previous print
    {
        System.out.print("\u001B[?25l"); // Hide the cursor
        System.out.print("\033[H\033[2J");  //Hides previous text
    }
}
