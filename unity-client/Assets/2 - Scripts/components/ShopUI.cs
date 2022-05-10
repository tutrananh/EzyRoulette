using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ShopUI : MonoBehaviour
{
    public static ShopUI INSTANCE;
    private static string addWithPaypalUrl = "http://localhost:8080/addBalance?username=";
    [SerializeField]
    private UniWebView webView;

    [SerializeField]
    private GameObject giftCardForm;

    [SerializeField]
    private GameObject paypalForm;

    [SerializeField]
    private InputField serial;

    [SerializeField]
    private InputField code;

    [SerializeField]
    private GameObject addBalanceResultForm;

    [SerializeField]
    private Text addBalanceResultText;
    public InputField Serial { get => serial; set => serial = value; }
    public InputField Code { get => code; set => code = value; }
    public Text AddBalanceResult { get => addBalanceResultText; set => addBalanceResultText = value; }

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
    public void OnClickAddBalance()
    {
        gameObject.SetActive(true);
    }
    public void OnClickBackToGame()
    {
      /*  if (paypalForm.activeSelf)
        {
            paypalForm.SetActive(false);
        }*/
        gameObject.SetActive(false);
    }
    public void OnClickPaypalButton()
    {/*
        paypalForm.SetActive(true);
        webView.Load(addWithPaypalUrl + GameUI.INSTANCE.Username);*/
        Application.OpenURL(addWithPaypalUrl + GameUI.INSTANCE.Username.text);
        Invoke("UpdateBalanceAfterTopup", 20);
    }
    void UpdateBalanceAfterTopup()
    {
        SocketRequest.GetInstance().sendUpdateBalanceRequest();
    }
    public void OnClickGiftCardButton()
    {
        giftCardForm.SetActive(true);
    }


    public void OnClickAddBalanceByGiftCard(int amount)
    {
        if (amount == 0)
        {
            addBalanceResultText.text = "Serial or Code is invalid! Please try again!!!";
        }
        else
        {
            giftCardForm.SetActive(false);
            addBalanceResultText.text = "Successfully Added "+ amount+" to balance!!!";
            Invoke("UpdateBalanceAfterTopup", 1);
        }
        addBalanceResultForm.SetActive(true);
        Invoke("HideAddBalanceResultForm", 2f);
    }
    public void HideAddBalanceResultForm()
    {
        addBalanceResultForm.SetActive(false);
    }
}
