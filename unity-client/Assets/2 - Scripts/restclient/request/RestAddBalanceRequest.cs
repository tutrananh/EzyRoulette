using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RestAddBalanceRequest : MonoBehaviour
{
    public static RestAddBalanceRequest INSTANCE;


    private string url = "http://localhost:8080/addGiftCard";
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

    public void SendAddBalanceByGiftCardRequestToHttpServer(string username, string serial, string code)
    {
        GiftCardRequestBody user = new GiftCardRequestBody(username, serial, code);
        Debug.Log(user.username);
        StartCoroutine(RestWebClient.Instance.HttpPost(url, JsonUtility.ToJson(user), (r) => RestAddBalanceResponse.INSTANCE.OnAddBalanceByGiftCardRequestComplete(r, username, serial, code), new List<RequestHeader>
        {
            contentTypeHeader
        }));
    }

}
