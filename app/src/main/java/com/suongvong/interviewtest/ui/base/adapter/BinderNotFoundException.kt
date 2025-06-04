package com.suongvong.interviewtest.ui.base.adapter

import java.lang.RuntimeException

class BinderNotFoundException(clazz: Class<*>) :
        RuntimeException("Have you registered {className}.class to the binder in the adapter/pool?".replace("{className}", clazz.simpleName))