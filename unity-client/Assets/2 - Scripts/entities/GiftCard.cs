using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GiftCard 
{
    private string serial;
    private string code;

    public GiftCard(string serial, string code)
    {
        this.serial = serial;
        this.code = code;
    }

    public string Serial { get => serial; set => serial = value; }
    public string Code { get => code; set => code = value; }
}
