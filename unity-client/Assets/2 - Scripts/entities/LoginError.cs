using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LoginError 
{
    private int id;
    private string error;

    public LoginError(int id, string error)
    {
        this.id = id;
        this.Error = error;
    }

    public string Error { get => error; set => error = value; }
}
