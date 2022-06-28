

public class GiftCardRequestBody 
{
	public string username = "";
	public string serial = "";
	public string code = "";


	public GiftCardRequestBody(string username, string serial, string code)
	{
		SetGiftCard(username, serial, code);
	}
	public void SetGiftCard(string username, string serial, string code)
	{
		this.username = username;
		this.serial = serial;
		this.code = code;
	}
}
