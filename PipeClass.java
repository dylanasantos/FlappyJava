public class PipeClass 
{
    private int pipe_size;
    private boolean is_split;

    String[] pipe_structure = {"  ", "  ", "  ", "  ", "  ", "  "}; //Array resembling height of map <- gets filled with pipes

    PipeClass()
    {
        is_split = ((int) (Math.random() * 2) == 1) ? true : false; //Randomizes split through 0 or 1 value -> converted to boolean
        pipe_size = 1 + (int) (Math.random() * 4);

        if (is_split)
        {
            create_split();
        }
        else
        {
            create_straight();
        }
    }

    private void create_straight()
    {
        boolean is_bottom =  ((int) (Math.random() * 2) == 1) ? true : false;
        if (is_bottom)
        {
            for (int i = 0; i < pipe_size; i++)
            {
                pipe_structure[pipe_structure.length - 1 - i] = "[]";
            }
        }
        else
        {
            for (int i = 0; i < pipe_size; i++)
            {
                pipe_structure[i] = "[]";
            }
        }
    }

    private void create_split()
    {
        int priority_randomizer = (int) (Math.random() * 2); //Allows for both 2-1 and 1-2 top-bottom pipe generation to occur

        int last_top_pos = -1;
        int last_bot_pos = pipe_structure.length;

        for(int i = 0; i < pipe_size; i++)
        {
            if (i % 2 == priority_randomizer)
            {
                pipe_structure[last_top_pos + 1] = "[]";
                last_top_pos += 1;
            }
            else
            {
                pipe_structure[last_bot_pos - 1] = "[]";
                last_bot_pos -= 1;
            }
        }
    }

    public boolean get_split()
    {
        return is_split;
    }

    public int get_size()
    {
        return pipe_size;
    }

    public String[] get_pipe()
    {
        return pipe_structure;
    }
}
