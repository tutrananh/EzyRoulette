using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class test : MonoBehaviour
{
    public Text textTest;
    public string baseUrl = "http://192.168.1.12:8080/login";
    RequestHeader contentTypeHeader = new RequestHeader
    {
        Key = "Content-Type",
        Value = "application/json"
    };
    // Start is called before the first frame update
    void Start()
    {
        UserRequestBody us = new UserRequestBody("tes", "123");
        Debug.Log(us.username);
        string testStr = JsonUtility.ToJson(us);
        Debug.Log(testStr);
        StartCoroutine(RestWebClient.Instance.HttpPost(baseUrl, testStr, (r) => OnRequestComplete(r), new List<RequestHeader>
        {
            contentTypeHeader
        }));
    }
    void OnRequestComplete(Response response)
    {
        Debug.Log($"Status Code: {response.StatusCode}");
        Debug.Log($"Data: {response.Data}");
        Debug.Log($"Error: {response.Error}");
        textTest.text = response.Data;
    }

}
