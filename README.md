Pjett
=====
u catch peepol
Server tar hand om beräkningar för kollision och vem som är, samt hur lång tid resp spelare har "varit".

Servern litar på klienten som skickar sin egen position (10ggr/sek?).
Servern beräknar kollision och skickar ut informationen till samtliga deltagare mha multicast, 5-10 ggr/sek(?).




Protokollspec:

Klient UDP:
CLI:SEQ:PORT:X:Y

Serv UDP/MC:

SRV:SEQ:#PLAYERS:1:X:Y:rotation:TMR:2:X:Y:rotation:TMR:3:X:Y:rotation:TMR:4:X:Y:rotation:TMR:PIATTE_ID:piaettarens ID

Dödsbud skickas med TCP innehållandes vilken spelare som dör.




Lobby med TCP (chatt med TCP, samt eventuella event-meddelanden).

- Under lobbysessionen ansluter resp spelare med UDP till servern för att servern skall kunna identifiera vilken spelare som skickar på vilken port under resterande del av sessionen.
