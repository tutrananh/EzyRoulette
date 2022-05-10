using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RestLoginRequest : MonoBehaviour
{
    public static RestLoginRequest INSTANCE;


    public string baseUrl = "http://192.168.1.12:8080/accountLogin";
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
    public void SendLoginRequestToHttpServer(string username, string password)
    {
        UserRequestBody user = new UserRequestBody(username, password);
        Debug.Log(user.username);
        string testStr = JsonUtility.ToJson(user);
        Debug.Log(testStr);
        StartCoroutine(RestWebClient.Instance.HttpPost(baseUrl, JsonUtility.ToJson(user), (r) => RestLoginResponse.INSTANCE.OnLoginRequestComplete(r, username, password), new List<RequestHeader>
        {
            contentTypeHeader
        }));
    }
    public void SendLoginWithGoogleRequestToHttpServer()
    {
        StartCoroutine(RestWebClient.Instance.HttpGet(googleTokenUrl, (r) => RestLoginResponse.INSTANCE.OnLoginWithGoogleRequestComplete(r)));
    }
}
