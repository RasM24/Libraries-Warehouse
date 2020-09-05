# Libraries-Warehouse
Общая кодовая база для проектов. Проекты, представленные в этом репозитории, хранятся в JFrog artifactory.
Подключение репозитория JFrog:
```kotlin
maven {
	url = "https://endroad.jfrog.io/artifactory/gradle-release-local/"
}
```
 ----------------

## Arena
Модуль, предназначенный для хранения какой-либо кодовой базы.
В библиотеке находятся те решения, которые:
- Не попали в другие модули
- Неизвестна их ценность(возможно этот код вообще будет удален и забыт)
- Служат записями для примера, что вот так тоже можно писать

### Подключение
```
implementation "ru.endroad:arena:1.0"
```

### Использование
*прим. здесь нет ни комментариев к функциям, ни краткого описания об их работе и цели. Только приведен список возможных функций

##### Image Converters
```
ImageBase64.asBitmap
Bitmap.toBase64
Image.asJPEG
Image.asYUV
YuvImage.asBitmap
```
##### Lifecycle
```
LiveData<T>.subcribe()
```
##### Assets Extension
```
AssetManager.openString()
AssetManager.openImage()
```
##### Room Extension
```
SupportSQLiteDatabase.runTransaction()
RoomDatabase.Builder<T>.preload()
```
##### Picasso
```
ImageView.load()
CircleTransform
```
##### View Extension
Toolbar.changeFont()
TextView.font

##### Context Extension
```
Context.startUrl()
Context.startEmail()
Context.startPhone()
Context.share()
```
##### MaskedEditText
*Концепция WarehouseLibrary не подразумевает хранение custom view. В ближайших обновлениях код будет удален
##### Argument Extension
```
Context.isLocationPermission()
AppCompatActivity.allPermissionsGranted
AppCompatActivity.requestPermissions()
String.permissionGranted()
isConnectedToNetwork()
```
##### Permission Extension
```
Fragment.withArgument()
DialogFragment.withArgument()
by argument()
by argument()
by argumentOptional()
by intent()
```

 ----------------

## Camp
//TODO описание

## Подключение
```
implementation "ru.endroad:camp:1.0"
```

## Использование
//TODO примеры использования

 ----------------

## Fragment Navigation
//TODO описание

## Подключение
```
implementation "ru.endroad:fragment-navigation:0.9"
```

## Использование
//TODO примеры использования

 ----------------

## MVI Core
//TODO описание

## Подключение
```
implementation "ru.endroad:mvi-core:1.0"
```

## Использование
//TODO примеры использования

 ----------------

## Vkontakte
//TODO описание

## Подключение
```
implementation "ru.endroad:vkontakte:0.9"
```

## Использование
//TODO примеры использования

 ----------------

## Simple FTP
//TODO описание

## Подключение
```
implementation "ru.endroad:simple-ftp:0.9"
```

## Использование
//TODO примеры использования

 ----------------

## Simple Telnet
//TODO описание

## Подключение
```
implementation "ru.endroad:simple-telnet:0.9"
```

## Использование
//TODO примеры использования
