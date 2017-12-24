package com.yugandhar.mdm.composite.service;
/* Generated Jul 3, 2017 2:32:54 PM by Hibernate Tools 5.2.1.Final using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar common license. */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.extern.transferobj.TxnPayload;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.corecomponent.LeIdentifierKycRegistryComponent;
import com.yugandhar.mdm.corecomponent.LePreferencesComponent;
import com.yugandhar.mdm.corecomponent.LeSystemKeysRegistryComponent;
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

/**
 * @author Yugandhar
 * @version 1.0
 * @since 1.0
 * @see Documentation
 */


@Scope(value = "prototype")
@Service("com.yugandhar.mdm.composite.service.UpdateLegalEntityService")
public class UpdateLegalEntityService {

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
	UpdateLePersonService updateLePersonService;

	@Autowired
	UpdateLeCorporationService updateLeCorporationService;

	@Autowired
	UpdateLeAddressService updateLeAddressService;

	@Autowired
	CreateLeAddressService createLeAddressService;

	@Autowired
	UpdateLePhoneService updateLePhoneService;

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
	UpdateLeAccountService updateLeAccountService;

	@Autowired
	UpdateLePropertyService updateLePropertyService;

	@Autowired
	UpdateLeVehicleService updateLeVehicleService;

	@Autowired
	MiscellaneousInfoComponent miscellaneousInfoComponent;

	public UpdateLegalEntityService() {
		txnTransferObjResponse = new TxnTransferObj();
		respLegalentityDO = new LegalentityDO();
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
			transitTxnTransferObj = legalentityComponent.merge(transitTxnTransferObj);
			respLegalentityDO = transitTxnTransferObj.getTxnPayload().getLegalentityDO();

			// process legal entities

			if (respLegalentityDO.getEntityObjectTypeRefkey().equals("1")) {
				// process Person
				if (null != reqlegalentityDO.getLePersonDO()) {
					transitTxnPayload = new TxnPayload();
					transitTxnPayload.setLePersonDO(reqlegalentityDO.getLePersonDO());
					transitTxnTransferObj.setTxnPayload(transitTxnPayload);
					transitTxnTransferObj = updateLePersonService.process(transitTxnTransferObj);
					respLegalentityDO.setLePersonDO(transitTxnTransferObj.getTxnPayload().getLePersonDO());
				}
			} else if (respLegalentityDO.getEntityObjectTypeRefkey().equals("2")) {
				if (null != reqlegalentityDO.getLeCorporationDO()) {
					// Process Corporation
					transitTxnPayload = new TxnPayload();
					transitTxnPayload.setLeCorporationDO(reqlegalentityDO.getLeCorporationDO());
					transitTxnTransferObj.setTxnPayload(transitTxnPayload);
					transitTxnTransferObj = updateLeCorporationService.process(transitTxnTransferObj);
					respLegalentityDO.setLeCorporationDO(transitTxnTransferObj.getTxnPayload().getLeCorporationDO());
				}
			}

			// Process LeSystemRegistryKeys
			if (null != reqlegalentityDO.getLeSystemKeysRegistryDOList()
					&& reqlegalentityDO.getLeSystemKeysRegistryDOList().size() > 0) {
				Iterator<LeSystemKeysRegistryDO> leSysKeyRegIterator = reqlegalentityDO.getLeSystemKeysRegistryDOList()
						.iterator();
				List<LeSystemKeysRegistryDO> respLeSystemKeysRegistryDOList = new ArrayList<LeSystemKeysRegistryDO>();

				while (leSysKeyRegIterator.hasNext()) {
					LeSystemKeysRegistryDO reqLeSystemKeysRegistryDO = leSysKeyRegIterator.next();

					if (null == reqLeSystemKeysRegistryDO.getIdPk() || reqLeSystemKeysRegistryDO.getIdPk().isEmpty()) {
						transitTxnPayload = new TxnPayload();
						reqLeSystemKeysRegistryDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeSystemKeysRegistryDO(reqLeSystemKeysRegistryDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = leSystemKeysRegistryComponent.persist(transitTxnTransferObj);
						respLeSystemKeysRegistryDOList
								.add(transitTxnTransferObj.getTxnPayload().getLeSystemKeysRegistryDO());

					} else {
						transitTxnPayload = new TxnPayload();
						reqLeSystemKeysRegistryDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeSystemKeysRegistryDO(reqLeSystemKeysRegistryDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = leSystemKeysRegistryComponent.merge(transitTxnTransferObj);
						respLeSystemKeysRegistryDOList
								.add(transitTxnTransferObj.getTxnPayload().getLeSystemKeysRegistryDO());
					}

				}
				respLegalentityDO.setLeSystemKeysRegistryDOList(respLeSystemKeysRegistryDOList);

			}

			// Process LePreferences
			if (null != reqlegalentityDO.getLePreferencesDOList()
					&& reqlegalentityDO.getLePreferencesDOList().size() > 0) {
				Iterator<LePreferencesDO> leSysKeyRegIterator = reqlegalentityDO.getLePreferencesDOList().iterator();
				List<LePreferencesDO> respLePreferencesDOList = new ArrayList<LePreferencesDO>();

				while (leSysKeyRegIterator.hasNext()) {
					LePreferencesDO reqLePreferencesDO = leSysKeyRegIterator.next();

					if (null == reqLePreferencesDO.getIdPk() || reqLePreferencesDO.getIdPk().isEmpty()) {
						transitTxnPayload = new TxnPayload();
						reqLePreferencesDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLePreferencesDO(reqLePreferencesDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = lePreferencesComponent.persist(transitTxnTransferObj);
						respLePreferencesDOList.add(transitTxnTransferObj.getTxnPayload().getLePreferencesDO());

					} else {
						transitTxnPayload = new TxnPayload();
						reqLePreferencesDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLePreferencesDO(reqLePreferencesDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = lePreferencesComponent.merge(transitTxnTransferObj);
						respLePreferencesDOList.add(transitTxnTransferObj.getTxnPayload().getLePreferencesDO());
					}

				}
				respLegalentityDO.setLePreferencesDOList(respLePreferencesDOList);

			}

			// Process LeIdentifierKycRegistry
			if (null != reqlegalentityDO.getLeIdentifierKycRegistryDOList()
					&& reqlegalentityDO.getLeIdentifierKycRegistryDOList().size() > 0) {
				Iterator<LeIdentifierKycRegistryDO> leSysKeyRegIterator = reqlegalentityDO
						.getLeIdentifierKycRegistryDOList().iterator();
				List<LeIdentifierKycRegistryDO> respLeIdentifierKycRegistryDOList = new ArrayList<LeIdentifierKycRegistryDO>();

				while (leSysKeyRegIterator.hasNext()) {
					LeIdentifierKycRegistryDO reqLeIdentifierKycRegistryDO = leSysKeyRegIterator.next();

					if (null == reqLeIdentifierKycRegistryDO.getIdPk()
							|| reqLeIdentifierKycRegistryDO.getIdPk().isEmpty()) {
						transitTxnPayload = new TxnPayload();
						reqLeIdentifierKycRegistryDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeIdentifierKycRegistryDO(reqLeIdentifierKycRegistryDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = leIdentifierKycRegistryComponent.persist(transitTxnTransferObj);
						respLeIdentifierKycRegistryDOList
								.add(transitTxnTransferObj.getTxnPayload().getLeIdentifierKycRegistryDO());

					} else {
						transitTxnPayload = new TxnPayload();
						reqLeIdentifierKycRegistryDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setLeIdentifierKycRegistryDO(reqLeIdentifierKycRegistryDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = leIdentifierKycRegistryComponent.merge(transitTxnTransferObj);
						respLeIdentifierKycRegistryDOList
								.add(transitTxnTransferObj.getTxnPayload().getLeIdentifierKycRegistryDO());
					}

				}
				respLegalentityDO.setLeIdentifierKycRegistryDOList(respLeIdentifierKycRegistryDOList);

			}

			// Process Addresses
			if (null != reqlegalentityDO.getLeAddressAssocDOList()) {

				Iterator<LeAddressAssocDO> addrIterator = reqlegalentityDO.getLeAddressAssocDOList().iterator();
				List<LeAddressAssocDO> respLeAddressAssocDOList = new ArrayList<LeAddressAssocDO>();

				while (addrIterator.hasNext()) {
					LeAddressAssocDO reqLeAddressAssocDO = addrIterator.next();
					// Process Addresses. if LeAddressAssocDO.IdPk is not null
					// means an update for the association else consider a new
					// association
					if (null != reqLeAddressAssocDO) {
						if (null != reqLeAddressAssocDO.getIdPk()) {
							transitTxnPayload = new TxnPayload();
							reqLeAddressAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
							transitTxnPayload.setLeAddressAssocDO(reqLeAddressAssocDO);
							transitTxnTransferObj.setTxnPayload(transitTxnPayload);
							transitTxnTransferObj = updateLeAddressService.process(transitTxnTransferObj);
							respLeAddressAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLeAddressAssocDO());

						} else {
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

			}

			// Process Phones
			if (null != reqlegalentityDO.getLePhoneAssocDOList()) {

				Iterator<LePhoneAssocDO> addrIterator = reqlegalentityDO.getLePhoneAssocDOList().iterator();
				List<LePhoneAssocDO> respLePhoneAssocDOList = new ArrayList<LePhoneAssocDO>();

				while (addrIterator.hasNext()) {
					LePhoneAssocDO reqLePhoneAssocDO = addrIterator.next();
					// Process Phonees. if LePhoneAssocDO.IdPk is not null
					// means an update for the association else consider a new
					// association
					if (null != reqLePhoneAssocDO) {
						if (null != reqLePhoneAssocDO.getIdPk()) {
							transitTxnPayload = new TxnPayload();
							reqLePhoneAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
							transitTxnPayload.setLePhoneAssocDO(reqLePhoneAssocDO);
							transitTxnTransferObj.setTxnPayload(transitTxnPayload);
							transitTxnTransferObj = updateLePhoneService.process(transitTxnTransferObj);
							respLePhoneAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLePhoneAssocDO());

						} else {
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

			}

			// Process Accounts
			if (null != reqlegalentityDO.getLeAccountAssocDOList()) {

				Iterator<LeAccountAssocDO> addrIterator = reqlegalentityDO.getLeAccountAssocDOList().iterator();
				List<LeAccountAssocDO> respLeAccountAssocDOList = new ArrayList<LeAccountAssocDO>();

				while (addrIterator.hasNext()) {
					LeAccountAssocDO reqLeAccountAssocDO = addrIterator.next();
					// Process Accountes. if LeAccountAssocDO.IdPk is not null
					// means an update for the association else consider a new
					// association
					if (null != reqLeAccountAssocDO) {
						if (null != reqLeAccountAssocDO.getIdPk()) {
							transitTxnPayload = new TxnPayload();
							reqLeAccountAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
							transitTxnPayload.setLeAccountAssocDO(reqLeAccountAssocDO);
							transitTxnTransferObj.setTxnPayload(transitTxnPayload);
							transitTxnTransferObj = updateLeAccountService.process(transitTxnTransferObj);
							respLeAccountAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLeAccountAssocDO());

						} else {
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

			}

			// Process Propertys
			if (null != reqlegalentityDO.getLePropertyAssocDOList()) {

				Iterator<LePropertyAssocDO> addrIterator = reqlegalentityDO.getLePropertyAssocDOList().iterator();
				List<LePropertyAssocDO> respLePropertyAssocDOList = new ArrayList<LePropertyAssocDO>();

				while (addrIterator.hasNext()) {
					LePropertyAssocDO reqLePropertyAssocDO = addrIterator.next();
					// Process Propertyes. if LePropertyAssocDO.IdPk is not null
					// means an update for the association else consider a new
					// association
					if (null != reqLePropertyAssocDO) {
						if (null != reqLePropertyAssocDO.getIdPk()) {
							transitTxnPayload = new TxnPayload();
							reqLePropertyAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
							transitTxnPayload.setLePropertyAssocDO(reqLePropertyAssocDO);
							transitTxnTransferObj.setTxnPayload(transitTxnPayload);
							transitTxnTransferObj = updateLePropertyService.process(transitTxnTransferObj);
							respLePropertyAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLePropertyAssocDO());

						} else {
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

			}

			// Process Vehicles
			if (null != reqlegalentityDO.getLeVehicleAssocDOList()) {

				Iterator<LeVehicleAssocDO> addrIterator = reqlegalentityDO.getLeVehicleAssocDOList().iterator();
				List<LeVehicleAssocDO> respLeVehicleAssocDOList = new ArrayList<LeVehicleAssocDO>();

				while (addrIterator.hasNext()) {
					LeVehicleAssocDO reqLeVehicleAssocDO = addrIterator.next();
					// Process Vehiclees. if LeVehicleAssocDO.IdPk is not null
					// means an update for the association else consider a new
					// association
					if (null != reqLeVehicleAssocDO) {
						if (null != reqLeVehicleAssocDO.getIdPk()) {
							transitTxnPayload = new TxnPayload();
							reqLeVehicleAssocDO.setLegalentityIdpk(respLegalentityDO.getIdPk());
							transitTxnPayload.setLeVehicleAssocDO(reqLeVehicleAssocDO);
							transitTxnTransferObj.setTxnPayload(transitTxnPayload);
							transitTxnTransferObj = updateLeVehicleService.process(transitTxnTransferObj);
							respLeVehicleAssocDOList.add(transitTxnTransferObj.getTxnPayload().getLeVehicleAssocDO());

						} else {
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

			}

			// Process MiscellaneousInfo
			if (null != reqlegalentityDO.getMiscellaneousInfoDOList()
					&& reqlegalentityDO.getMiscellaneousInfoDOList().size() > 0) {
				Iterator<MiscellaneousInfoDO> itrMiscellaneousInfo = reqlegalentityDO.getMiscellaneousInfoDOList()
						.iterator();
				List<MiscellaneousInfoDO> respMiscellaneousInfoDOList = new ArrayList<MiscellaneousInfoDO>();

				while (itrMiscellaneousInfo.hasNext()) {
					MiscellaneousInfoDO reqMiscellaneousInfoDO = itrMiscellaneousInfo.next();

					if (null == reqMiscellaneousInfoDO.getIdPk() || reqMiscellaneousInfoDO.getIdPk().isEmpty()) {
						transitTxnPayload = new TxnPayload();
						reqMiscellaneousInfoDO.setEntityObjectTypeRefkey("3");
						reqMiscellaneousInfoDO.setEntityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setMiscellaneousInfoDO(reqMiscellaneousInfoDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = miscellaneousInfoComponent.persist(transitTxnTransferObj);
						respMiscellaneousInfoDOList.add(transitTxnTransferObj.getTxnPayload().getMiscellaneousInfoDO());

					} else {
						transitTxnPayload = new TxnPayload();
						reqMiscellaneousInfoDO.setEntityObjectTypeRefkey("3");
						reqMiscellaneousInfoDO.setEntityIdpk(respLegalentityDO.getIdPk());
						transitTxnPayload.setMiscellaneousInfoDO(reqMiscellaneousInfoDO);
						transitTxnTransferObj.setTxnPayload(transitTxnPayload);
						transitTxnTransferObj = miscellaneousInfoComponent.merge(transitTxnTransferObj);
						respMiscellaneousInfoDOList.add(transitTxnTransferObj.getTxnPayload().getMiscellaneousInfoDO());
					}

				}
				respLegalentityDO.setMiscellaneousInfoDOList(respMiscellaneousInfoDOList);

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
					"UpdateLegalEntityService failed unexpectedly");

		}
		respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
		return respTxnTransferObj;

	}

}
