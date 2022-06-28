using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RestLoginRequest : MonoBehaviour
{
    public static RestLoginRequest INSTANCE;


    private string normalLoginUrl = "http://localhost:8080/accountLogin";
    private string registryUrl = "http://localhost:8080/accountRegistry";
    private string googleTokenUrl = "http://localhost:8080/getGoogleLoginToken";
    RequestHeader contentTypeHeader = new RequestHeader
    {
        Key = "Content-Type",
        Value = "application/json"
    };
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
    public void SendRegistryRequestToHttpServer(string username, string password)
    {
        UserRequestBody user = new UserRequestBody(username, password);
        Debug.Log(user.username);
        StartCoroutine(RestWebClient.Instance.HttpPost(registryUrl, JsonUtility.ToJson(user), (r) => RestLoginResponse.INSTANCE.OnRegistryRequestComplete(r, username, password), new List<RequestHeader>
        {
            contentTypeHeader
        }));
    }
    public void SendLoginRequestToHttpServer(string username, string password)
    {
        UserRequestBody user = new UserRequestBody(username, password);
        Debug.Log(user.username);
        StartCoroutine(RestWebClient.Instance.HttpPost(normalLoginUrl, JsonUtility.ToJson(user), (r) => RestLoginResponse.INSTANCE.OnLoginRequestComplete(r, username, password), new List<RequestHeader>
        {
            contentTypeHeader
        }));
    }
    public void SendLoginWithGoogleRequestToHttpServer()
    {
        StartCoroutine(RestWebClient.Instance.HttpGet(googleTokenUrl, (r) => RestLoginResponse.INSTANCE.OnLoginWithGoogleRequestComplete(r)));
    }
}
