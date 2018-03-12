# Android 权限动态检测

From : [AndPermission](https://github.com/yanzhenjie/AndPermission)





- # API_LEVEL >= 23 M 6.0 <br>
  **MRequest**<br>
  **check**: <br>
        1. Context.checkPermission<br>
        2. AppOpsManager.permissionToOp(permission);
  <br>
  **Request**:<br>
  startActivity -> PermissionActivity<br>
  PermissionActivity -> onRequestPermissionsResult -> static listener -> finish<br>
  **doubleCheck**<br>
	1. Context.checkPermission<br>
    2. AppOpsManager.permissionToOp(permission);
    3. run test about permission


- # API_LEVEL < 23  <br>
  **LRequest**<br>
  **check**: <br>
        1. API_LEVEL < 21 L 5.0 ，return true
        2. run test about permission 
  <br>
  **Request**:<br>
	无  
  **doubleCheck**<br>
	无

- # 跳转到权限设置界面
	huawei<br>
	xiaomi<br>
	vivo<br>
	oppo<br>
	meizu<br>
	smartisan<br>
	samsung<br>
	commonDevice
  
<img src="https://github.com/yuanbinbinbin/demo2/blob/master/onlinepic/permission.gif" alt="permission.gif" />