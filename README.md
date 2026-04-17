# 🎮 GyroMove — 2D мобильная игра с управлением через гироскоп

## 📌 Краткие сведения о проекте

**Название:** GyroMove  
**Платформа:** Android  
**Язык разработки:** Kotlin  
**Среда разработки:** Android Studio  
**Тип приложения:** 2D игра  
**Метод управления:** гироскоп (датчик наклона устройства)

---

##  Описание проекта

Данное приложение представляет собой простую 2D игру, в которой игрок управляет персонажем с помощью наклона смартфона. Управление реализовано через встроенные сенсоры устройства (акселерометр/гироскоп). Персонаж перемещается по экрану в зависимости от угла наклона телефона.

---

##  Используемые технологии и методы

###  Технологии:
- Kotlin
- Android SDK
- SurfaceView (для отрисовки графики)
- SensorManager (работа с датчиками)
- Canvas API (2D отрисовка)

---

###  Используемые алгоритмы и методы:

#### 1. Игровой цикл (Game Loop)
Реализован через отдельный поток (`GameThread`):

- обновление состояния игры (`update()`)
- отрисовка кадра (`draw()`)

```text
update → draw → sleep → повтор
```

---

#### 2. Обработка данных гироскопа

Используются значения:

- `SensorEvent.values[0]` — наклон по X
- `SensorEvent.values[1]` — наклон по Y

Формула движения:

```kotlin
x -= tiltX * speed
y += tiltY * speed
```

---

#### 3. Рендеринг 2D графики

Используется `Canvas`:

- очистка экрана
- отрисовка спрайта игрока
- трансформация (rotate)

---

##  Архитектура приложения

###  Модули:

### 1. MainActivity
- Точка входа в приложение
- Запускает `GameView`
- Фиксирует ориентацию экрана

---

### 2. GameView
- Основной игровой экран
- Управляет игровым циклом
- Отвечает за update/draw

---

### 3. GameThread
- Реализация игрового цикла
- Контроль FPS
- Вызов update и draw

---

### 4. Player
- Хранение позиции персонажа
- Отрисовка спрайта
- Обработка движения

---

### 5. TiltController
- Чтение данных гироскопа
- Передача наклона в GameView

---

##  Взаимодействие модулей

```text
TiltController → GameView → Player → Canvas
                     ↑
               GameThread
```

---

##  Листинг ключевого кода

###  MainActivity

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(GameView(this))
    }
}
```

---

###  Player (движение + отрисовка)

```kotlin
class Player(
    var x: Float,
    var y: Float,
    context: Context
) {

    private val bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.player
    )

    var tiltX = 0f
    var tiltY = 0f

    fun update(tiltX: Float, tiltY: Float) {
        this.tiltX = tiltX
        this.tiltY = tiltY

        x -= tiltX * 5
        y += tiltY * 5
    }

    fun draw(canvas: Canvas) {
        canvas.save()
        canvas.rotate(tiltX * 5, x, y)

        canvas.drawBitmap(
            bitmap,
            x - bitmap.width / 2,
            y - bitmap.height / 2,
            null
        )

        canvas.restore()
    }
}
```

---

###  GameThread

```kotlin
class GameThread(
    private val surfaceHolder: SurfaceHolder,
    private val gameView: GameView
) : Thread() {

    var running = false

    override fun run() {
        while (running) {
            val canvas = surfaceHolder.lockCanvas()

            synchronized(surfaceHolder) {
                gameView.update()
                gameView.draw(canvas)
            }

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}
```

---

##  Входные и выходные данные

### Вход:
- tiltX, tiltY (гироскоп)

### Выход:
- изменение координат игрока
- отрисовка на экране

---

##  Тестирование

✔ приложение запускается  
✔ управление работает  
✔ спрайт отображается  
✔ движение плавное  
✔ критических ошибок нет  

---

##  Запуск

1. Открыть проект (папку GyroMove) в Android Studio  
2. Подключить устройство  
3. Run ▶  

ИЛИ СКАЧАТЬ APK ФАЙЛ И ЕГО УСТАНОВИТЬ

APK:
app/build/outputs/apk/debug/app-debug.apk
