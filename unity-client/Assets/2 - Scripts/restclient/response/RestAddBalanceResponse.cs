using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RestAddBalanceResponse : MonoBehaviour
{
    public static RestAddBalanceResponse INSTANCE;
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
    public void OnAddBalanceByGiftCardRequestComplete(Response response, string username, string serial, string code)
    {
        Debug.Log($"Status Code: {response.StatusCode}");
        Debug.Log($"Data: {response.Data}");
        Debug.Log($"Error: {response.Error}");
        HandleAddBalanceByGiftCardResponse(response, username, serial, code);
    }
    void HandleAddBalanceByGiftCardResponse(Response response, string username, string serial, string code)
    {
        string res = response.Data;
        if (res.Equals("0"))
        {
            ShopUI.INSTANCE.OnClickAddBalanceByGiftCard(0);
        }
        else
        {
            int amount = int.Parse(res);
            ShopUI.INSTANCE.OnClickAddBalanceByGiftCard(amount);
        }
    }
}
