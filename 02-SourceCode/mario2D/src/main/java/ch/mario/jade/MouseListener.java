package ch.mario.jade;

import java.util.concurrent.RecursiveTask;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener
{
    /**/
    private static MouseListener _instance;
    private double _scrollX, _scrollY;
    private double _posX, _posY, _lastX, _lastY;
    private boolean _mouseButtonPressed[] = new boolean[3];
    private boolean _isDragging;

    /*
    * GETER SETTER
    */
    public static float GetX()
    {
        return (float)Get()._posX;
    }
    public static float GetY()
    {
        return (float)Get()._posY;
    }

    public static float GetDx() // Difference behind the first and last pos X
    {
        return (float)(Get()._lastX - Get()._posX);
    }
    public static float GetDy() // Difference behind the first and last pos Y
    {
        return (float)(Get()._lastY - Get()._posY);
    }

    public static float GetScrollX()
    {
        return (float)Get()._scrollX;
    }
    public static float GetScrollY()
    {
        return (float)Get()._scrollY;
    }

    public static boolean IsDragging()
    {
        return Get()._isDragging;
    }
    public static boolean MousButtonPressed(int buttonPosition)
    {
        if (buttonPosition < Get()._mouseButtonPressed.length)
        {
            return Get()._mouseButtonPressed[buttonPosition];
        }
        else
        {
            return false;
        }
    }

    /*
    * Class constructor
    */
    private MouseListener()
    {
        _scrollX = 0.0;
        _scrollY = 0.0;
        _posX = 0.0;
        _posY = 0.0;
        _lastX = 0.0;
        _lastY = 0.0;
    }

    /*
    * Methods
    */
    public static MouseListener Get()
    {
        if (_instance == null)
        {
            _instance = new MouseListener();
        }

        return _instance;
    }

    public static void MousePosCallback(long window, double posX, double posY)
    {
        Get()._lastX = Get()._posX;
        Get()._lastY = Get()._posY;

        Get()._posX = posX;
        Get()._posY = posY;

        Get()._isDragging = Get()._mouseButtonPressed[0] || Get()._mouseButtonPressed[1] || Get()._mouseButtonPressed[2];
    }

    public static void MousButtonCallback(long window, int buttonPosition, int action, int mods/* Modifier => CTRL + C */)
    {
        // Check if the button positon in out of the array
        if (buttonPosition < Get()._mouseButtonPressed.length)
        {
            // IF => Check if the button is pressed
            // ELSE IF => Check if the button is not pressed (released)
            if (action == GLFW_PRESS)
            {
                Get()._mouseButtonPressed[buttonPosition] = true;
            }
            else if (action == GLFW_RELEASE)
            {
                Get()._mouseButtonPressed[buttonPosition] = false;
                Get()._isDragging = false;
            }
        }
    }

    public static void MouseScrollCallback(long window, double offsetX, double offsetY)
    {
        Get()._scrollX = offsetX;
        Get()._scrollY = offsetY;
    }

    public static void EndFrame()
    {
        Get()._scrollX = 0;
        Get()._scrollY = 0;
        Get()._lastX = Get()._posX;
        Get()._lastY = Get()._posY;
    }
}
