package com.nulabinc.zxcvbn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MeasureTest {
    private String password;

    public MeasureTest(String password) {
        this.password = password;
    }

    @Test
    public void testMeasure() throws Exception {
        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);
        assertEquals("Unexpected error. Password is " + password, password, strength.getPassword());
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"qwER43@!"},
                {"Tr0ub4dour&3"},
                {"correcthorsebatterystaple"},
                {"password"},
                {"drowssap"},
                {"passwordp"},
                {"passwordadmin"},
                {"p@$$word@dmin"},
                {"19700101"},
                {"20300101"},
                {"aaaaaaaaa"},
                {"123456789"},
                {"abcdefghijklmnopqrstuvwxyz"},
                {"qwertyuiop@["},
                {"zxcvbnm,./_"},
                {"asdfghjkl;:]"},
                {"pandapandapandapandapandapandapandapandapandaa"},
                {"appleappleappleappleappleappleappleappleapplea"},
                {"dncrbliehbvkehr734yf;ewhihwfph@houaegfueqpg30^r0urfvhej¥]e;l,ckvniwbgoidnci@oewhfoobojabouhqwou12482386fhoiwehe@o"},
                {"apple orenge aabb "},
                {"eTq($%u-44c_j9NJB45a#2#JP7sH"},
                {"IB7~EOw!51gug+7s#+%A9P1O/w8f"},
                {"1v_f%7JvS8w!_t398+ON-CObI#v0"},
                {"8lFmfc0!w)&iU9DM6~4_w)D)Y44J"},
                {"&BZ09gjG!iKG&#M09s_1Gr41&o%i"},
                {"T9Y-!ciS%XW9U5l/~aw9+4!5u8Ti"},
                {"QMji&0uze5O#%+%2e_Y08E(R6L8p"},
                {"6EG4y1nJASd!1~!//#6+Yhb1vW3d"},
                {"8$q_5f2U3s6~W(S7iv)_8N%lJkOE"},
                {"%nbd~$)2y/6hV6)2R9vYPpA49A~C"},
                {"Rh&pW%EXT=/Z1lzouG.wU_+2MT+FG4sm+&jqN?L25jDtjW3EQuppfvD_30Vo3K=SX4=z3-U2gVf7A0oSM5oWegRa_sV$-GLI3LzCo&@!h@$v#OkoN#@-eS8Y&W$pGmmVXc#XHAv?n$M+_wQx1FAB_*iaZE1_9ZV.cwn-d@+90B8z0bVOKc63lV9QntW0kryN7Y#rjv@0+Bd8hc-3WW_Yn%z5/DE?R*UeiKgR#$/F8kA9I!Ib*GDa.x0T7UWCCxDV&ithebyz$=7vW6TdmlmL%WZxmA7K%*Rg1035UO%WOTIgiMs4AjpmL1"}
        });
    }
}
