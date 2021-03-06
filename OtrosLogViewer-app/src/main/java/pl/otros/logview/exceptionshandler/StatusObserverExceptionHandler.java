/*
 * Copyright 2012 Krzysztof Otrebski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.otros.logview.exceptionshandler;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Logger;

import pl.otros.logview.gui.StatusObserver;

public class StatusObserverExceptionHandler implements UncaughtExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger(StatusObserverExceptionHandler.class.getName());
	
	private StatusObserver statusObserver;
	
	public StatusObserverExceptionHandler(StatusObserver statusObserver) {
		super();
		this.statusObserver = statusObserver;
	}


	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {		
		String msg = String.format("Error in thread \"%s\": %s", thread.getName(),throwable.getLocalizedMessage());
		statusObserver.updateStatus(msg, StatusObserver.LEVEL_ERROR);		
	}

}
