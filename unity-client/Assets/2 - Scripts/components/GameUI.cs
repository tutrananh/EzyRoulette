using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameUI: MonoBehaviour
{
    public static GameUI INSTANCE;

    [SerializeField]
    private Text resultText;

    [SerializeField]
    private Text timer;

    [SerializeField]
    private Text username;

    [SerializeField]
    private Text balanceText;

    [SerializeField]
    private Text redBettorsText;
    [SerializeField]
    private Text greenBettorsText;

    [SerializeField]
    private InputField betInputField;

    [SerializeField]
    private Button betOnRedBtn;

    [SerializeField]
    private Button betOnGreenBtn;

    [SerializeField]
    private InputField chatInputField;

    [SerializeField]
    private GameObject chatBoxContents;


    private float currentTime, delayToNewRoundTime;
	private bool spinned;

    private void Awake()
    {
        currentTime = 99f;
        delayToNewRoundTime = 10f;
        spinned = false;
        // start of new code
        if (INSTANCE != null)
        {
            Destroy(gameObject);
            return;
        }
        // end of new code

        INSTANCE = this;
    }
    public float CurrentTime { get => currentTime; set => currentTime = value; }
    public float DelayToNewRoundTime { get => delayToNewRoundTime; set => delayToNewRoundTime = value; }
    public bool Spinned { get => spinned; set => spinned = value; }
    public Text Timer { get => timer; set => timer = value; }
    public Text Username { get => username; set => username = value; }
    public Text BalanceText { get => balanceText; set => balanceText = value; }
    public Text RedBettorsText { get => redBettorsText; set => redBettorsText = value; }
    public Text GreenBettorsText { get => greenBettorsText; set => greenBettorsText = value; }
    public InputField BetInputField { get => betInputField; set => betInputField = value; }
    public InputField ChatInputField { get => chatInputField; set => chatInputField = value; }
    public Button BetOnRedBtn { get => betOnRedBtn; set => betOnRedBtn = value; }
    public Button BetOnGreenBtn { get => betOnGreenBtn; set => betOnGreenBtn = value; }

    public void updateChatBox(List<string> chatContents)
    {
        Text[] contents = chatBoxContents.GetComponentsInChildren<Text>();
        if(chatContents.Count>0)
        {
            for (int i = 0; i < chatContents.Count; i++)
            {
                contents[i].text = chatContents[i];
            }
        }
    }


}
