using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UserRequestBody 
{
	public string username = "";
	public string password = "";


	public UserRequestBody(string username, string password)
	{
		SetUser(username, password);
	}
	public void SetUser(string username, string password)
	{
		this.username = username;
		this.password = password;
	}
}
