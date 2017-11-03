package io.github.marcelovca90.helper;

import org.junit.Test;

import io.github.marcelovca90.helper.MailHelper.CryptoProtocol;

public class MailHelperTest
{
    private final String filename = getClass().getClassLoader().getResource("tinylog.properties").getFile();

    @Test
    public void sendMail_usingSSLandFakeCredentials_shouldReturnSuccessSuppressingException()
    {
        MailHelper.sendMail(CryptoProtocol.SSL, "username", "password", "hostname", "from", "recipients", "subject", "text", filename);
    }

    @Test
    public void sendMail_usingTLSandFakeCredentials_shouldReturnSuccessSuppressingException()
    {
        MailHelper.sendMail(CryptoProtocol.TLS, "username", "password", "hostname", "from", "recipients", "subject", "text", filename);
    }
}
