package com.yugandhar.jms;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.transobj.TxnTransferObj;

@Component
public class YugJMSMessageSender {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);
	private static final Logger mqReqResplogger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_MQ_REQ_RESP_LOGGER);

	@Resource(mappedName = "java:jboss/com/yugandhar/DefaultPooledConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Autowired
	JmsTemplate jmsTemplate;

	@Resource(mappedName = "java:jboss/com/yugandhar/default/requestQueue")
	private Destination yugDefaultRequestQueueDestination;

	@Resource(mappedName = "java:jboss/com/yugandhar/default/responseQueue")
	private Destination yugDefaultResponseQueueDestination;

	// Text Message to Default RESPONSE Queue
	public void sendTextMessageToDefaultResponseQueue(String strMessage) {

		if(logger.isInfoEnabled()) {
		logger.info("sending TextMessageToDefaultResponseQueue: ");
		}
		
		if (mqReqResplogger.isDebugEnabled()) {
			mqReqResplogger.debug("sending MessageToDefaultResponseQueue: RESPONSE MESSAGE: " + strMessage);
		}

		jmsTemplate.send(yugDefaultResponseQueueDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(strMessage);
			}
		});
	}

	// Message to Default RESPONSE Queue
	public void sendMessageToDefaultResponseQueue(TxnTransferObj txnTransferObj) {
		if(logger.isInfoEnabled()) {
		logger.info("sending MessageToDefaultResponseQueue: " + txnTransferObj);
		}
		
		if (mqReqResplogger.isDebugEnabled()) {
			mqReqResplogger.debug("sending MessageToDefaultResponseQueue: RESPONSE MESSAGE: " + txnTransferObj);
		}
		
		jmsTemplate.convertAndSend(yugDefaultResponseQueueDestination, txnTransferObj);

	}

	// Message to Default REQUEST Queue
	public void sendMessageToDefaultRequestQueue(TxnTransferObj txnTransferObj) {
		if(logger.isInfoEnabled()) {
		logger.info("sending MessageToDefaultRequestQueue: " + txnTransferObj);
		}
		
		if (mqReqResplogger.isDebugEnabled()) {
			mqReqResplogger.debug("sending MessageToDefaultRequestQueue: RESPONSE MESSAGE: " + txnTransferObj);
		}
		
		jmsTemplate.convertAndSend(yugDefaultRequestQueueDestination, txnTransferObj);

	}

	// Text Message to Default REQUEST Queue
	public void sendTextMessageToDefaultRequestQueue(String strMessage) {
		if(logger.isInfoEnabled()) {
		logger.info("Sending TextMessageToDefaultRequestQueue: " );
		}
		
		if (mqReqResplogger.isDebugEnabled()) {
			mqReqResplogger.debug("Sending TextMessageToDefaultRequestQueue: RESPONSE MESSAGE: " + strMessage);
		}
		
		jmsTemplate.send(yugDefaultRequestQueueDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(strMessage);
			}
		});
	}

	public void sendMessageToQueue(TxnTransferObj txnTransferObj, String queueName) {
		if(logger.isInfoEnabled()) {
		logger.info("Sending MessageToQueue: " + queueName + " #" + txnTransferObj);
		}
		
		if (mqReqResplogger.isDebugEnabled()) {
			mqReqResplogger.debug("Sending MessageToQueue: " + queueName + " RESPONSE MESSAGE: " + txnTransferObj);
		}
		
		jmsTemplate.convertAndSend(queueName, txnTransferObj);

	}
}