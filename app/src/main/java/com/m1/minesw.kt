package com.m1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*

class minesw(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {
    var surfaceHolder: SurfaceHolder = this.holder
    private var thread: Thread? = null
    var mine: Canvas? = null
    var backC: Canvas? = null
    var backG: Bitmap? = null
    var mi: Bitmap? = null
    var paint = Paint()
    var isD = 0
    var Dx = 0
    var Dy = 0

    @JvmField
    var isR = 0
    var ss = 0
    override fun surfaceCreated(holder: SurfaceHolder) {}
    fun resume() {
        // TODO Auto-generated method stub
        isRun = true
        thread = Thread(MyThread())
        thread!!.start()
    }

    fun pause() {
        // TODO Auto-generated method stub
        isRun = false
        try {
            thread!!.join()
        } catch (e: InterruptedException) {
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        println(height.toString() + "dd" + width)
        mx = width
        my = height
        isGet = true
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val tx: Float
        val ty: Float
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                tx = motionEvent.x
                ty = motionEvent.y
                if (tx < lx * l1 && ty < ly * l1 && tx > l1 && ty > l1) {
                    isD = 1
                    Dx = (tx - l1).toInt() / l1
                    Dy = (ty - l1).toInt() / l1
                }
            }
        }
        return true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    fun Ra(x: Int, y: Int): Int {
        return (Math.random() * (y - x + 1) + x).toInt()
    }

    fun initView() {
        var j: Int
        var k: Int
        ss = 0
        val xx = BitmapFactory.Options()
        xx.inScaled = false
        xx.inPreferredConfig = Bitmap.Config.ARGB_8888
        backG = Bitmap.createBitmap(mx, my, Bitmap.Config.ARGB_8888)
        mi = BitmapFactory.decodeResource(resources, R.drawable.a5, xx)
        backC = Canvas(backG!!)
        backC!!.drawARGB(255, 255, 255, 255)
        paint.setARGB(255, 255, 0, 0)
        paint.strokeWidth = 6f
        l1 = my / (ly + 1)
        lx = (ly * 1.5f).toInt()
        m1 = lx - 1
        m2 = ly - 1
        ccx = Array(m1) { IntArray(m2) }
        bbx = Array(m1) { IntArray(m2) }
        ddx = Array(m1) { IntArray(m2) }
        k = 0
        while (k < m1) {
            j = 0
            while (j < m2) {
                ccx[k][j] = 0
                bbx[k][j] = 0
                ddx[k][j] = 0
                j++
            }
            k++
        }
        var n = number
        while (n > 0) {
            k = Ra(0, m1 - 1)
            j = Ra(0, m2 - 1)
            while (ccx[k][j] == 1) {
                k = Ra(0, m1 - 1)
                j = Ra(0, m2 - 1)
            }
            ccx[k][j] = 1
            n--
        }
        var a: Int
        var b: Int
        k = 0
        while (k < m1) {
            j = 0
            while (j < m2) {
                bbx[k][j] = 0
                a = -1
                while (a <= 1) {
                    b = -1
                    while (b <= 1) {
                        if (k + a >= 0 && j + b >= 0 && k + a < m1 && j + b < m2) {
                            if (ccx[k + a][j + b] == 1) {
                                bbx[k][j]++
                            }
                        }
                        b++
                    }
                    a++
                }
                if (ccx[k][j] == 1) {
                    bbx[k][j] = -1
                }
                j++
            }
            k++
        }
        j = 0
        while (j < ly) {
            backC!!.drawLine(
                l1.toFloat(),
                (l1 * j + l1).toFloat(),
                ly * l1 * 1.5f,
                (l1 * j + l1).toFloat(),
                paint
            )
            j++
        }
        j = 0
        while (j < lx) {
            backC!!.drawLine(
                (l1 * j + l1).toFloat(),
                l1.toFloat(),
                (l1 * j + l1).toFloat(),
                (ly * l1).toFloat(),
                paint
            )
            j++
        }
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER
    }

    inner class Mmx(var a: Int, var b: Int)

    fun drawn(x: Int, y: Int) {
        val ax: MutableList<Mmx> = ArrayList()
        var bb: Mmx
        ax.add(Mmx(x, y))
        var u: Int
        var v: Int
        var x1: Int
        var x2: Int
        var j: Int = 0
        var k: Int = 1
        var dx: Int
        var dy: Int
        var s: Int
        var s1: Int
        while (j < k) {
            bb = ax[j]
            x1 = bb.a
            x2 = bb.b
            u = -1
            while (u <= 1) {
                v = -1
                while (v <= 1) {
                    dx = x1 + u
                    dy = x2 + v
                    if (dx >= 0 && dy >= 0 && dx < m1 && dy < m2) {
                        ddx[dx][dy] = 1
                        paint.setARGB(255, 100, 100, 100)
                        backC!!.drawRect(
                            Rect(
                                dx * l1 + l1 + 3,
                                dy * l1 + l1 + 3,
                                dx * l1 + 2 * l1 - 3,
                                dy * l1 + 2 * l1 - 3
                            ), paint
                        )
                        if (bbx[dx][dy] != 0) {
                            paint.setARGB(255, 0, 0, 255)
                            backC!!.drawText(
                                "" + bbx[dx][dy],
                                (dx * l1 + 3 * l1 / 2).toFloat(),
                                (dy * l1 + 3 * l1 / 2 + 18).toFloat(),
                                paint
                            )
                        }
                        if (bbx[dx][dy] == 0 && (u != 0 || v != 0)) {
                            s1 = 0
                            s = 0
                            while (s < k) {
                                if (dx == ax[s].a && dy == ax[s].b) {
                                    s1 = 1
                                    break
                                }
                                s++
                            }
                            if (s1 == 0) {
                                paint.setARGB(255, 100, 100, 100)
                                backC!!.drawRect(
                                    Rect(
                                        dx * l1 + l1 + 3,
                                        dy * l1 + l1 + 3,
                                        dx * l1 + 2 * l1 - 3,
                                        dy * l1 + 2 * l1 - 3
                                    ), paint
                                )
                                ax.add(Mmx(dx, dy))

                                // System.out.println(ax.size());
                                k++
                            }
                        }
                    }
                    v++
                }
                u++
            }
            j++
        }
        ax.clear()
    }

    internal inner class MyThread : Runnable {
        override fun run() {
            println("555")
            while (!isGet) {
            }
            initView()
            while (isRun) {
                if (surfaceHolder.surface.isValid) {
                    if (isR == 1) {
                        isR = 0
                        ss = 0
                        initView()
                    }
                    mine = surfaceHolder.lockCanvas()
                    mine!!.drawBitmap(backG!!, 0.0f, 0.0f, paint)
                    /* paint.setTextSize(50);
                    paint.setARGB(255,255,255,0);
                    mine.drawText(""+SS,100,100,paint);*/if (isD == 1) {
                        isD = 0
                        if (ccx[Dx][Dy] == 0) {
                            paint.setARGB(255, 100, 100, 100)
                            backC!!.drawRect(
                                Rect(
                                    Dx * l1 + l1 + 3,
                                    Dy * l1 + l1 + 3,
                                    Dx * l1 + 2 * l1 - 3,
                                    Dy * l1 + 2 * l1 - 3
                                ), paint
                            )
                            if (bbx[Dx][Dy] != 0) {
                                paint.textSize = 50f
                                paint.setARGB(255, 0, 0, 255)
                                paint.textAlign = Paint.Align.CENTER
                                backC!!.drawText(
                                    "" + bbx[Dx][Dy],
                                    (Dx * l1 + 3 * l1 / 2).toFloat(),
                                    (Dy * l1 + 3 * l1 / 2 + 18).toFloat(),
                                    paint
                                )
                                ddx[Dx][Dy] = 1
                            }
                            if (bbx[Dx][Dy] == 0) {
                                drawn(Dx, Dy)
                            }
                            var k: Int
                            var j: Int
                            ss = 0
                            k = 0
                            while (k < m1) {
                                j = 0
                                while (j < m2) {
                                    ss += ddx[k][j]
                                    j++
                                }
                                k++
                            }
                            if (ss == m1 * m2 - number) {
                                paint.textSize = 300f
                                paint.setARGB(120, 255, 255, 0)
                                backC!!.drawText("你赢了！", 800f, 500f, paint)
                            }
                        } else {
                            backC!!.drawBitmap(
                                mi!!, Rect(0, 0, 640, 640), Rect(
                                    Dx * l1 + l1 + 3,
                                    Dy * l1 + l1 + 3,
                                    Dx * l1 + 2 * l1 - 3,
                                    Dy * l1 + 2 * l1 - 3
                                ), paint
                            )
                            var k: Int
                            var j: Int
                            k = 0
                            while (k < m1) {
                                j = 0
                                while (j < m2) {
                                    if (ccx[k][j] == 0) {
                                        paint.setARGB(255, 100, 100, 100)
                                        backC!!.drawRect(
                                            Rect(
                                                k * l1 + l1 + 3,
                                                j * l1 + l1 + 3,
                                                k * l1 + 2 * l1 - 3,
                                                j * l1 + 2 * l1 - 3
                                            ), paint
                                        )
                                        if (bbx[k][j] != 0) {
                                            paint.textSize = 50f
                                            paint.setARGB(255, 0, 0, 255)
                                            paint.textAlign = Paint.Align.CENTER
                                            backC!!.drawText(
                                                "" + bbx[k][j],
                                                (k * l1 + 3 * l1 / 2).toFloat(),
                                                (j * l1 + 3 * l1 / 2 + 18).toFloat(),
                                                paint
                                            )
                                        }
                                    } else {
                                        backC!!.drawBitmap(
                                            mi!!, Rect(0, 0, 640, 640), Rect(
                                                k * l1 + l1 + 3,
                                                j * l1 + l1 + 3,
                                                k * l1 + 2 * l1 - 3,
                                                j * l1 + 2 * l1 - 3
                                            ), paint
                                        )
                                    }
                                    j++
                                }
                                k++
                            }
                            paint.textSize = 300f
                            paint.setARGB(120, 0, 255, 255)
                            backC!!.drawText("你输了！", 800f, 500f, paint)
                        }
                    }
                    surfaceHolder.unlockCanvasAndPost(mine)
                }
            }
        }
    }

    companion object {
        var isRun = false
        var isGet = false

        @JvmField
        var SS = 0
        var mx = 0
        var my = 0
        var ly = 10
        var l1 = 0
        var lx = 0
        var l2 = 0
        var m1 = 0
        var m2 = 0
        lateinit var bbx: Array<IntArray>
        lateinit var ccx: Array<IntArray>
        lateinit var ddx: Array<IntArray>
        var number = 12
    }

    init {
        surfaceHolder.addCallback(this)
    }
}