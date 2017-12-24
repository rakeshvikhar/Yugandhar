package com.yugandhar.cache.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.misc.dobj.CommonValidationResponse;

@RestController
@RequestMapping("/rest")
public class DefaultCacheRestController {
	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	@Autowired
	CommonValidationUtil commonValidationUtil;
	CommonValidationResponse commonValidationResponse;

	@Autowired
	RequestProcessor requestProcessor;

	@RequestMapping(value = "/YugandharRequestProcessor", headers = "Accept=application/json", method = RequestMethod.POST)
	public TxnTransferObj YugandharRequestProcessor(@RequestBody TxnTransferObj txnTransferObj) {
		try {
			return requestProcessor.processMessage(txnTransferObj);
		} catch (Exception e) {

			logger.error("Transaction failed", e);
			TxnTransferObj txnErrTransferObj = new TxnTransferObj();
			txnErrTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
			if (e instanceof YugandharCommonException) {
				YugandharCommonException yce = (YugandharCommonException) e;
				txnErrTransferObj.getTxnPayload().setErrorResponseObj(
						commonValidationUtil.createCommonValidationResponseFromYugException(txnErrTransferObj, yce));

			} else {
				txnErrTransferObj.getTxnPayload().setErrorResponseObj(
						commonValidationUtil.createCommonValidationResponseFromException(txnErrTransferObj, "1", e));

			}
			return txnErrTransferObj;

		}

	}

}
