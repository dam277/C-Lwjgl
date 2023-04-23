package ch.mario.jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private int _width, _height;
    private String _title;
    private long _glfwWindow;
    private static Window _windowInstance = null;
    private float _r, _g, _b, _a;
    private boolean _fadeToBlack = false;

    private Window()
    {
        _width = 1920;
        _height = 1080;
        _title = "Mario";
        _r = 1;
        _g = 1;
        _b = 1;
        _a = 1;
    }

    public static Window Get()
    {
        if (_windowInstance == null)
        {
            _windowInstance = new Window();
        }

        return _windowInstance;
    }

    public void Run()
    {
        System.out.println("Version : " + Version.getVersion() + "!");

        this.Init();
        this.Loop();

        /* The os do that automatically but this is to be proper */
        //Free the memory
        glfwFreeCallbacks(_glfwWindow);

        // Destroy window
        glfwDestroyWindow(_glfwWindow);

        // Terminate GLFW and the free the errors callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void Init()
    {
        // Set an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to init GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        _glfwWindow = glfwCreateWindow(this._width, this._height, this._title, NULL, NULL);

        if (_glfwWindow == NULL)
        {
            throw new IllegalStateException("Failed to create GLFW Window");
        }

        // Set the cursor
        glfwSetCursorPosCallback(_glfwWindow, MouseListener::MousePosCallback);
        glfwSetMouseButtonCallback(_glfwWindow, MouseListener::MousButtonCallback);
        glfwSetScrollCallback(_glfwWindow, MouseListener::MouseScrollCallback);

        // Set the keyboard
        glfwSetKeyCallback(_glfwWindow, KeyListener::KeyCallback);

        // Make opengl context current
        glfwMakeContextCurrent(_glfwWindow);

        // Enable vsync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(_glfwWindow);

        GL.createCapabilities();
    }

    public void Loop()
    {
        while (!glfwWindowShouldClose(_glfwWindow))
        {
            // Poll events (Key listener)
            glfwPollEvents();

            glClearColor(_r, _g, _b, _a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (_fadeToBlack)
            {
                _r = Math.max(_r - 0.05f, 0);
                _g = Math.max(_g - 0.001f, 11);
                _b = Math.max(_b - 0.01f, 0);
            }

            if (KeyListener.IsKeyPressed(GLFW_KEY_SPACE))
            {
                _fadeToBlack = true;
            }

            glfwSwapBuffers(_glfwWindow);
        }
    }
}
