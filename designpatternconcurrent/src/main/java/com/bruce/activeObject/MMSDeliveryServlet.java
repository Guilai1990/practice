package com.bruce.activeObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;


/**
 * @Author: Bruce
 * @Date: 2019/6/4 21:22
 * @Version 1.0
 */
public class MMSDeliveryServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(MMSDeliverRequest.class);

    private static final long serialVersionUID = -5177695993579658630L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MMSDeliverRequest mmsDeliveryRequest = this.parseRequest(req.getInputStream());
        Recipient shortNumberRecipient = mmsDeliveryRequest.getRecipient();
        Recipient originalNumberRecipient = null;

        try {
            originalNumberRecipient = convertShortNumber(shortNumberRecipient);
        } catch (SQLException e) {

            AsyncRequestPersistence.getInstance().store(mmsDeliveryRequest);

            resp.setStatus(202);

        }

        LOG.info(String.valueOf(originalNumberRecipient));
    }

    private MMSDeliverRequest parseRequest(InputStream reqInputStream) {

        MMSDeliverRequest mmsDeliverRequest = new MMSDeliverRequest();
        return mmsDeliverRequest;

    }

    private Recipient convertShortNumber(Recipient shortNumberRecipient) throws SQLException {
        Recipient recipient = null;

        return recipient;
    }


}
