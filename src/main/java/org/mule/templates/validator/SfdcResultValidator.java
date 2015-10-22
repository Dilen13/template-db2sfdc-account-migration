package org.mule.templates.validator;


import org.mule.api.MuleEvent;
import org.mule.extension.validation.api.ValidationResult;
import org.mule.extension.validation.api.Validator;
import org.mule.extension.validation.internal.ImmutableValidationResult;
import org.mule.modules.salesforce.bulk.EnrichedUpsertResult;

import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.StatusCode;

public class SfdcResultValidator implements Validator {

	@Override
	public ValidationResult validate(MuleEvent event) {
		EnrichedUpsertResult result = (EnrichedUpsertResult) event.getMessage().getPayload();
		for (Error e : result.getErrors()) {
			if (e.getStatusCode() == StatusCode.REQUIRED_FIELD_MISSING ||
				e.getStatusCode() == StatusCode.INVALID_TYPE_ON_FIELD_IN_RECORD) {
				return ImmutableValidationResult.error(e.getMessage());
			}
			
			/*ImmutableBatchJobResult a;
			a.getResultForStep("getAccountInSalesforceStep").getExceptionSummary().getExceptionsCount().keySet();
			a.getLoadingPhaseException();*/
		}
		return ImmutableValidationResult.ok();
	}
}
