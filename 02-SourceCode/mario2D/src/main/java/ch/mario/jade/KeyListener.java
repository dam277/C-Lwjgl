package ch.mario.jade;

import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener
{
    private static KeyListener _instance;
    private boolean _keyPressed[] = new boolean[350];

    private KeyListener()
    {

    }

    public static KeyListener Get()
    {
        if (_instance == null)
        {
            _instance = new KeyListener();
        }

        return _instance;
    }

    public static void KeyCallback(long window, int keyPosition, int scancode, int action, int mods)
    {
        if (action == GLFW_PRESS)
        {
            Get()._keyPressed[keyPosition] = true;
        }
        else if (action == GLFW_RELEASE)
        {
            Get()._keyPressed[keyPosition] = false;
        }
    }

    public static boolean IsKeyPressed(int keyCode)
    {
        return Get()._keyPressed[keyCode];
    }
}
