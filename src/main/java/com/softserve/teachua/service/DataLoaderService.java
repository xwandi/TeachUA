package com.softserve.teachua.service;

import com.softserve.teachua.dto.databaseTransfer.ExcelParsingData;

/**
 * This interface contains all needed methods to manage data loader.
 */

public interface DataLoaderService {
    /**
     * The method loads data from excel to database.
     */
    void loadToDatabase(ExcelParsingData excelParsingData);
}
