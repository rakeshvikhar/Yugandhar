package com.yugandhar.mdm.composite.service;
/* Generated Jul 3, 2017 2:32:54 PM by Hibernate Tools 5.2.1.Final using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar common license. */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yugandhar.common.constant.yugandharConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.extern.transferobj.TxnPayload;
import com.yugandhar.mdm.corecomponent.LeIdentifierKycRegistryComponent;
import com.yugandhar.mdm.corecomponent.LePreferencesComponent;
import com.yugandhar.mdm.corecomponent.LeSystemKeysRegistryComponent;
import com.yugandhar.mdm.corecomponent.LeToLeRelationshipComponent;
import com.yugandhar.mdm.corecomponent.LegalentityComponent;
import com.yugandhar.mdm.corecomponent.MiscellaneousInfoComponent;
import com.yugandhar.mdm.extern.dobj.LeAccountAssocDO;
import com.yugandhar.mdm.extern.dobj.LeAddressAssocDO;
import com.yugandhar.mdm.extern.dobj.LeIdentifierKycRegistryDO;
import com.yugandhar.mdm.extern.dobj.LePhoneAssocDO;
import com.yugandhar.mdm.extern.dobj.LePreferencesDO;
import com.yugandhar.mdm.extern.dobj.LePropertyAssocDO;
import com.yugandhar.mdm.extern.dobj.LeSystemKeysRegistryDO;
import com.yugandhar.mdm.extern.dobj.LeVehicleAssocDO;
import com.yugandhar.mdm.extern.dobj.LegalentityDO;
import com.yugandhar.mdm.extern.dobj.MiscellaneousInfoDO;
import com.yugandhar.mdm.misc.dobj.PrimaryKeyDO;

/**
 * @author Yugandhar
 * @version 1.0
 * @since 1.0
 * @see Documentation
 */

@Scope(value = "prototype")
@Service("com.yugandhar.mdm.composite.service.CreateLegalEntityService")
public class CreateLegalEntityService {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	TxnTransferObj txnTransferObjResponse;
	LegalentityDO respLegalentityDO;
	TxnTransferObj respTxnTransferObj;
	TxnPayload respTxnPayload;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	LegalentityComponent legalentityComponent;

	@Autowired
	CreateLePersonService createLePersonService;

	@Autowired
	CreateLeCorporationService createLeCorporationService;

	@Autowired
	CreateLeAddressService createLeAddressService;

	@Autowired
	CreateLePhoneService createLePhoneService;

	@Autowired
	LeSystemKeysRegistryComponent leSystemKeysRegistryComponent;

	@Autowired
	LePreferencesComponent lePreferencesComponent;

	@Autowired
	LeIdentifierKycRegistryComponent leIdentifierKycRegistryComponent;

	@Autowired
	CreateLeAccountService createLeAccountService;

	@Autowired
	CreateLePropertyService createLePropertyService;

	@Autowired
	CreateLeVehicleService createLeVehicleService;

	@Autowired
	MiscellaneousInfoComponent miscellaneousInfoComponent;

	@Autowired
	LeToLeRelationshipComponent leToLeRelationshipComponent;

	public CreateLegalEntityService() {
		txnTransferObjResponse = new TxnTransferObj();
		respTxnTransferObj = new TxnTransferObj();
		respTxnPayload = new TxnPayload();
	}

	@Transactional
	public TxnTransferObj process(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		TxnTransferObj transitTxnTransferObj = new TxnTransferObj();
		TxnPayload transitTxnPayload = null;
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
		transitTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			// TODO logic here

			LegalentityDO reqlegalentityDO = txnTransferObj.getTxnPayload().getLegalentityDO();

			// persist base legal entity
			transitTxnPayload = new TxnPayload();
			transitTxnPayload.setLegalentityDO(reqlegalentityDO);
			transitTxnTransferObj.setTxnPayload(transitTxnPayload);
			transitTxnTransferObj = legalentityComponent.persist(transitTxnTransferObj);
			respLegalentityDO = transitTxnTransferObj.getTxnPayload().getLegalentityDO();

			// Validate LePerson or LeCorporation Objects
			if (null == reqlegalentityDO.getLePersonDO() && null == reqlegalentityDO.getLeCorporationDO()) {
				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "10048",
						"Validation failed - Either lePersonDO or leCorporationDO is required in the request");
			}

			if (respLegalentityDO.getEntityObjectTypeRefkey().equals("1") && null == reqlegalentityDO.getLePersonDO()) {
				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "10049",
						"Validation failed - required data object as per entityObjectTypeRefkey not present");

			} else if (respLegalentityDO.getEntityObjectTypeRefkey().equals("2")
					&& null == reqlegalentityDO.getLeCorporationDO()) {
				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "10049",
						"Validation failed - required data object as per entityObjectTypeRefkey not present");

			}

			// process legal entities
			if (respLegalentityDO.getEntityObjectTypeRefkey().equals("1")) {
				// Process Person
				PrimaryKeyDO primaryKeyDO=new PrimaryKeyDO();
				primaryKeyDO.setIdPk(respLegalentityDO.getIdPk());
				reqlegalentityDO.getLePersonDO().setPrimaryKeyDO(primaryKeyDO);
				
				transitTxnPayload = new TxnPayload();
				transitTxnPayload.setLePersonDO(reqlegalentityDO.getLePersonDO());
				transitTxnTransferObj.setTxnPayload(transitTxnPayload);
				transitTxnTransferObj = createLePersonService.process(transitTxnTransferObj);
				respLegalentityDO.setLePersonDO(transitTxnTransferObj.getTxnPayload().getLePersonDO());

			} else if (respLegalentityDO.getEntityObjectTypeRefkey().equals("2")) {
				// Process Corporation
				PrimaryKeyDO primaryKeyDO=new PrimaryKeyDO();
				primaryKeyDO.setIdPk(respLegalentityDO.getIdPk());
				reqlegalentityDO.getLeCorporationDO().setPrimaryKeyDO(primaryKeyDO);
				
				transitTxnPayload = new TxnPayload();
				transitTxnPayload.setLeCorporationDO(reqlegalentityDO.getLeCorporationDO());
				transitTxnTransferObj.setTxnPayload(transitTxnPayload);
				transitTxnTransferObj = createLeCorporationService.process(transitTxnTransferObj);
				respLegalentityDO.setLeCorporationDO(transitTxnTransferObj.getTxnPayload().getLeCorporationDO());
			} else {
				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "10026",
						"Validation failed - EntityObjectTypeRefkey is not valid");
			}

			// Process LeSystemRegistryKeys
			if (null != reqlegalentityDO.getLeSystemKeysRegistryDOList()
					&& reqlegalentityDO.getLeSystemKeysRegistryDOList().size() > 0) {
				Iterator<LeSystemKeysRegistryDO> leSysKeyRegIterator = reqlegalentityDO.getLeSystemKeysRegistryDOList()
						.iterator();
				List<LeSystemKeysRegistryDO> respLePhoneAssocDOList = new ArrayList<LeSystemKeysRegistryDO>();

				while (leSysKeyRegIterator.hasNext()) {
					LeSystemKeysRegistryDO reqLeSystemKeysRegistryDO = leSysKeyRegIterator.next();

					if (null != reqLeSystemKeysRegistryDO) {
						transitTxnPayload = new TxnPayload();
						reqLeSystemKeysRegistryDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeSystemKeysRegistryDO(reqLeSystemKeysRegistryDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = leSystemKeysRegistryComponent.persist(transitTxnTransferObj);
						respLePhoneAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLeSystemKeysRegistryDO());

					}

				}
				respLegalentityDO.setLeSystemKeysRegistryDOList(respLePhoneAssocDOList);

			}

			// Process LePreferences
			if (null != reqlegalentityDO.getLePreferencesDOList()
					&& reqlegalentityDO.getLePreferencesDOList().size() > 0) {
				Iterator<LePreferencesDO> leprefIterator = reqlegalentityDO.getLePreferencesDOList().iterator();
				List<LePreferencesDO> respLePhoneAssocDOList = new ArrayList<LePreferencesDO>();

				while (leprefIterator.hasNext()) {
					LePreferencesDO reqLePreferencesDO = leprefIterator.next();

					if (null != reqLePreferencesDO) {
						transitTxnPayload = new TxnPayload();
						reqLePreferencesDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLePreferencesDO(reqLePreferencesDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = lePreferencesComponent.persist(transitTxnTransferObj);
						respLePhoneAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLePreferencesDO());

					}

				}
				respLegalentityDO.setLePreferencesDOList(respLePhoneAssocDOList);

			}

			// Process LeIdentifierKYC
			if (null != reqlegalentityDO.getLeIdentifierKycRegistryDOList()
					&& reqlegalentityDO.getLeIdentifierKycRegistryDOList().size() > 0) {
				Iterator<LeIdentifierKycRegistryDO> leIdentifierKycIterator = reqlegalentityDO
						.getLeIdentifierKycRegistryDOList().iterator();
				List<LeIdentifierKycRegistryDO> respLePhoneAssocDOList = new ArrayList<LeIdentifierKycRegistryDO>();

				while (leIdentifierKycIterator.hasNext()) {
					LeIdentifierKycRegistryDO reqLeIdentifierKycRegistryDO = leIdentifierKycIterator.next();

					if (null != reqLeIdentifierKycRegistryDO) {
						transitTxnPayload = new TxnPayload();
						reqLeIdentifierKycRegistryDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeIdentifierKycRegistryDO(reqLeIdentifierKycRegistryDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = leIdentifierKycRegistryComponent.persist(transitTxnTransferObj);
						respLePhoneAssocDOList
								.add(transitTxnTransferObj.getTxnPayload().getLeIdentifierKycRegistryDO());

					}

				}
				respLegalentityDO.setLeIdentifierKycRegistryDOList(respLePhoneAssocDOList);

			}

			// Process Addresses
			if (null != reqlegalentityDO.getLeAddressAssocDOList()) {

				Iterator<LeAddressAssocDO> addrIterator = reqlegalentityDO.getLeAddressAssocDOList().iterator();
				List<LeAddressAssocDO> respLeAddressAssocDOList = new ArrayList<LeAddressAssocDO>();

				while (addrIterator.hasNext()) {
					LeAddressAssocDO reqLeAddressAssocDO = addrIterator.next();

					if (null != reqLeAddressAssocDO) {
						transitTxnPayload = new TxnPayload();
						reqLeAddressAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeAddressAssocDO(reqLeAddressAssocDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = createLeAddressService.process(transitTxnTransferObj);
						respLeAddressAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLeAddressAssocDO());

					}

				}
				respLegalentityDO.setLeAddressAssocDOList(respLeAddressAssocDOList);

			}

			// process Phone associations
			if (null != reqlegalentityDO.getLePhoneAssocDOList()) {

				Iterator<LePhoneAssocDO> addrIterator = reqlegalentityDO.getLePhoneAssocDOList().iterator();
				List<LePhoneAssocDO> respLePhoneAssocDOList = new ArrayList<LePhoneAssocDO>();

				while (addrIterator.hasNext()) {
					LePhoneAssocDO reqLePhoneAssocDO = addrIterator.next();

					if (null != reqLePhoneAssocDO) {
						transitTxnPayload = new TxnPayload();
						reqLePhoneAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLePhoneAssocDO(reqLePhoneAssocDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = createLePhoneService.process(transitTxnTransferObj);
						respLePhoneAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLePhoneAssocDO());

					}

				}
				respLegalentityDO.setLePhoneAssocDOList(respLePhoneAssocDOList);

			}

			// Process Accounts
			if (null != reqlegalentityDO.getLeAccountAssocDOList()) {

				Iterator<LeAccountAssocDO> addrIterator = reqlegalentityDO.getLeAccountAssocDOList().iterator();
				List<LeAccountAssocDO> respLeAccountAssocDOList = new ArrayList<LeAccountAssocDO>();

				while (addrIterator.hasNext()) {
					LeAccountAssocDO reqLeAccountAssocDO = addrIterator.next();

					if (null != reqLeAccountAssocDO) {
						transitTxnPayload = new TxnPayload();
						reqLeAccountAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeAccountAssocDO(reqLeAccountAssocDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = createLeAccountService.process(transitTxnTransferObj);
						respLeAccountAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLeAccountAssocDO());

					}

				}
				respLegalentityDO.setLeAccountAssocDOList(respLeAccountAssocDOList);

			}

			// Process Propertys
			if (null != reqlegalentityDO.getLePropertyAssocDOList()) {

				Iterator<LePropertyAssocDO> addrIterator = reqlegalentityDO.getLePropertyAssocDOList().iterator();
				List<LePropertyAssocDO> respLePropertyAssocDOList = new ArrayList<LePropertyAssocDO>();

				while (addrIterator.hasNext()) {
					LePropertyAssocDO reqLePropertyAssocDO = addrIterator.next();

					if (null != reqLePropertyAssocDO) {
						transitTxnPayload = new TxnPayload();
						reqLePropertyAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLePropertyAssocDO(reqLePropertyAssocDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = createLePropertyService.process(transitTxnTransferObj);
						respLePropertyAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLePropertyAssocDO());

					}

				}
				respLegalentityDO.setLePropertyAssocDOList(respLePropertyAssocDOList);

			}

			// Process Vehicles
			if (null != reqlegalentityDO.getLeVehicleAssocDOList()) {

				Iterator<LeVehicleAssocDO> addrIterator = reqlegalentityDO.getLeVehicleAssocDOList().iterator();
				List<LeVehicleAssocDO> respLeVehicleAssocDOList = new ArrayList<LeVehicleAssocDO>();

				while (addrIterator.hasNext()) {
					LeVehicleAssocDO reqLeVehicleAssocDO = addrIterator.next();

					if (null != reqLeVehicleAssocDO) {
						transitTxnPayload = new TxnPayload();
						reqLeVehicleAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeVehicleAssocDO(reqLeVehicleAssocDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = createLeVehicleService.process(transitTxnTransferObj);
						respLeVehicleAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLeVehicleAssocDO());

					}

				}
				respLegalentityDO.setLeVehicleAssocDOList(respLeVehicleAssocDOList);

			}

			// Process MiscellaneousInfo
			if (null != reqlegalentityDO.getMiscellaneousInfoDOList()
					&& reqlegalentityDO.getMiscellaneousInfoDOList().size() > 0) {
				Iterator<MiscellaneousInfoDO> itrMiscellaneousInfoDO = reqlegalentityDO.getMiscellaneousInfoDOList()
						.iterator();
				List<MiscellaneousInfoDO> respLePhoneAssocDOList = new ArrayList<MiscellaneousInfoDO>();

				while (itrMiscellaneousInfoDO.hasNext()) {
					MiscellaneousInfoDO reqMiscellaneousInfoDO = itrMiscellaneousInfoDO.next();

					if (null != reqMiscellaneousInfoDO) {
						transitTxnPayload = new TxnPayload();
						reqMiscellaneousInfoDO.setEntityObjectTypeRefkey("3");
						reqMiscellaneousInfoDO.setEntityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setMiscellaneousInfoDO(reqMiscellaneousInfoDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = miscellaneousInfoComponent.persist(transitTxnTransferObj);
						respLePhoneAssocDOList.add(transitTxnTransferObj.getTxnPayload().getMiscellaneousInfoDO());

					}

				}
				respLegalentityDO.setMiscellaneousInfoDOList(respLePhoneAssocDOList);

			}

			// Final Response object
			respTxnPayload.setLegalentityDO(respLegalentityDO);
			respTxnTransferObj.setTxnPayload(respTxnPayload);

		} catch (YugandharCommonException yce) {
			logger.error("Composite Service failed", yce);
			throw yce;
		} catch (Exception e) {
			// write the logic to get error message based on error code. Error
			// code is hard-coded here
			logger.error("persist failed", e);
			e.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "5", e,
					"createLegalEntityService failed unexpectedly");

		}
		respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
		return respTxnTransferObj;

	}

}
