using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RestLoginResponse : MonoBehaviour
{

    private static string usernameError = "Invalid Username!";
    private static string passwordError = "Password is incorrect!";
    public static RestLoginResponse INSTANCE;
    // Start is called before the first frame update
    void Start()
    {
        if (INSTANCE != null)
        {
            Destroy(gameObject);
            return;
        }
        // end of new code

        INSTANCE = this;
    }
    public void OnRegistryRequestComplete(Response response, string username, string password)
    {
        Debug.Log($"Status Code: {response.StatusCode}");
        Debug.Log($"Data: {response.Data}");
        Debug.Log($"Error: {response.Error}");
        HandleRegistryResponse(response, username, password);
    }
    void HandleRegistryResponse(Response response, string username, string password)
    {
        HandleLoginResponse(response, username, password);
    }

    public void OnLoginRequestComplete(Response response, string username, string password)
    {
        Debug.Log($"Status Code: {response.StatusCode}");
        Debug.Log($"Data: {response.Data}");
        Debug.Log($"Error: {response.Error}");
        HandleLoginResponse(response, username, password);
    }
    void HandleLoginResponse(Response response, string username, string password)
    {
        string loginToken = response.Data;
        if (loginToken.Equals(usernameError))
        {
            LoginUI.INSTANCE.HandleError(usernameError);
        }
        else
        {
            if (loginToken.Equals(passwordError))
            {
                LoginUI.INSTANCE.HandleError(passwordError);
            }
            else
            {
                SocketProxy.GetInstance().login(username, password, loginToken);
            }
        }
    }

    public void OnLoginWithGoogleRequestComplete(Response response)
    {
        Debug.Log($"Status Code: {response.StatusCode}");
        Debug.Log($"Data: {response.Data}");
        Debug.Log($"Error: {response.Error}");
        HandleGoogleLoginResponse(response.Data);
    }
    void HandleGoogleLoginResponse(string loginToken)
    {
        string username = "user" + loginToken.Substring(0,6);
        if (!loginToken.Equals(""))
        {
            SocketProxy.GetInstance().login(username, loginToken, loginToken);
        }
    }
}
