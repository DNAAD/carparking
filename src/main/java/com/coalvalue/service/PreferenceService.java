package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;

import java.util.Map;

//import org.springframework.security.core.Authentication;

/**
 * Created by silence yuan on 2015/6/28.
 */
public interface PreferenceService extends BaseService {


    OperationResult changeDefaultStorage(StorageSpace storage, User user);

    Map getPreference(User principal);

    Preference getPreference(Employee employee);

    Map getStorage(StorageSpace storageSpace);
}
