using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LoginUI : MonoBehaviour
{
    public static LoginUI INSTANCE;

    public InputField usernameInput;
    public InputField passwordInput;

    [SerializeField]
    private InputField usernameRegistryInput;

    [SerializeField]
    private InputField passwordRegistryInput;

    [SerializeField]
    private GameObject registryUI;

    [SerializeField]
    private GameObject messagePopup;


    [SerializeField]
    private Text messageText;

    private void Awake()
    {
        // start of new code
        if (INSTANCE != null)
        {
            Destroy(gameObject);
            return;
        }
        // end of new code

        INSTANCE = this;
    }
    public void OnClickLogin()
    {
        Debug.Log("Username: " + usernameInput.text);
        Debug.Log("Password: " + passwordInput.text);
        SocketProxy.GetInstance().login(usernameInput.text, passwordInput.text, 0);
    }

    public void OnClickRegisterNow()
    {
        registryUI.SetActive(true);
    }
    public void OnClickSignup()
    {
        Debug.Log("Username: " + usernameRegistryInput.text);
        Debug.Log("Password: " + passwordRegistryInput.text);
        SocketProxy.GetInstance().login(usernameRegistryInput.text, passwordRegistryInput.text,1);
        registryUI.SetActive(false);
    }
    public void OnClickReturnToLogin()
    {
        registryUI.SetActive(false);
    }

    public void CloseMessagePopup()
    {
        messagePopup.SetActive(false);
    }

    public void HandleError(string error)
    {
        messageText.text = error;
        messagePopup.SetActive(true);
        Invoke("CloseMessagePopup", 2);
    }

}
