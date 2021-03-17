package com.m1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.Config.ARGB_8888;


public class minesw extends SurfaceView implements Callback {

    SurfaceHolder surfaceHolder;
    Thread thread;
    Canvas mine,backC;
    Bitmap backG,mi;
    Paint paint=new Paint();
    static boolean isRun=false,isGet=false;
    static public int SS=0,mx,my;
    static int ly=10,l1,lx,l2,m1,m2;
    static int[][] bbx;
    static int[][] ccx;
    static int[][] ddx;
    static int number=12;
    int isD=0,Dx,Dy;
    public int isR=0;
    int ss=0;




    public minesw(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder=this.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    public void resume(){
        // TODO Auto-generated method stub
        isRun=true;
        thread=new Thread(new MyThread());
        thread.start();
    }

    public void pause(){
        // TODO Auto-generated method stub
        isRun=false;
        try {
            thread.join();
        }
        catch (InterruptedException e){

        }

    }



    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height) {
        System.out.println(height+"dd"+width);
        mx=width;
        my=height;
        isGet=true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float tx,ty;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                tx=motionEvent.getX();
                ty=motionEvent.getY();
                if((tx<lx*l1)&&(ty<ly*l1)&&(tx>l1)&&(ty>l1)){
                    isD=1;
                    Dx=(int)(tx-l1)/l1;
                    Dy=(int)(ty-l1)/l1;
                }

                break;


        }

        return true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {


    }


    int Ra(int x,int y){
        return (int)(Math.random()*(y-x+1)+x);
    }

    public void initView(){
        int j,k;
        ss=0;
        BitmapFactory.Options xx=new BitmapFactory.Options();
        xx.inScaled=false;
        xx.inPreferredConfig=ARGB_8888;
        backG=Bitmap.createBitmap(mx,my,ARGB_8888);
        mi= BitmapFactory.decodeResource(getResources(),R.drawable.a5,xx);
        backC=new Canvas(backG);
        backC.drawARGB(255,255,255,255);
        paint.setARGB(255,255,0,0);
        paint.setStrokeWidth(6);
        l1=my/(ly+1);
        lx=(int)(ly*1.5f);
        m1=lx-1;
        m2=ly-1;




        ccx=new int[m1][m2];
        bbx=new int[m1][m2];
        ddx=new int[m1][m2];
        for(k=0;k<m1;k++){
            for(j=0;j<m2;j++){
                ccx[k][j]=0;
                bbx[k][j]=0;
                ddx[k][j]=0;
            }
        }

        int n=number;
        while(n>0){
            k=Ra(0,m1-1);
            j=Ra(0,m2-1);
            while(ccx[k][j]==1){
                k=Ra(0,m1-1);
                j=Ra(0,m2-1);
            }
            ccx[k][j]=1;
            n--;
        }
        int a,b;
        for(k=0;k<m1;k++){
            for(j=0;j<m2;j++){
                bbx[k][j]=0;
                for(a=-1;a<=1;a++){
                    for(b=-1;b<=1;b++){
                        if((k+a>=0)&&(j+b>=0)&&(k+a<m1)&&(j+b<m2)){
                            if(ccx[k+a][j+b]==1){
                                bbx[k][j]++;
                            }


                        }
                    }
                }
                if(ccx[k][j]==1){
                    bbx[k][j]=-1;
                }
            }
        }


        for(j=0;j<ly;j++){
            backC.drawLine(l1,l1*j+l1,ly*l1*1.5f,l1*j+l1,paint);
        }
        for(j=0;j<lx;j++){
            backC.drawLine(l1*j+l1,l1,l1*j+l1,ly*l1,paint);
        }
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);

    }

    public class Mmx{
        public int a;
        public int b;
        public Mmx(int x,int y){
            a=x;
            b=y;
        }

    }

    public void drawn(int x, int y){

        List<Mmx> ax=new ArrayList<>();
        Mmx bb;
        ax.add(new Mmx(x,y));
        int j,k=1;
        int u,v;
        int x1,x2;
        j=0;
        k=1;
        int dx,dy;
        int s,s1=0;
        while(j<k){
            bb=ax.get(j);
            x1=bb.a;
            x2=bb.b;
            for(u=-1;u<=1;u++){
                for(v=-1;v<=1;v++){
                    dx=x1+u;
                    dy=x2+v;
                    if((dx>=0)&&(dy>=0)&&(dx<m1)&&(dy<m2)){
                        ddx[dx][dy]=1;
                        paint.setARGB(255,100,100,100);
                        backC.drawRect(new Rect(dx*l1+l1+3,dy*l1+l1+3,dx*l1+2*l1-3,dy*l1+2*l1-3),paint);
                        if(bbx[dx][dy]!=0){
                            paint.setARGB(255,0,0,255);
                            backC.drawText(""+bbx[dx][dy],dx*l1+3*l1/2,dy*l1+3*l1/2+18,paint);
                        }

                        if((bbx[dx][dy]==0)&&((u!=0)||(v!=0))){
                            s1=0;
                            for(s=0;s<k;s++){
                                if((dx==ax.get(s).a)&&(dy==ax.get(s).b)){
                                    s1=1;
                                    break;
                                }
                            }
                            if(s1==0){
                                paint.setARGB(255,100,100,100);
                                backC.drawRect(new Rect(dx*l1+l1+3,dy*l1+l1+3,dx*l1+2*l1-3,dy*l1+2*l1-3),paint);
                                ax.add(new Mmx(dx,dy));

                               // System.out.println(ax.size());
                                k++;

                            }

                        }
                    }
                }
            }
            j++;


        }
        ax.clear();

    }




    class MyThread implements Runnable
    {

        @Override
        public void run() {
            System.out.println("555");
            while (isGet==false){

            }
            initView();
            while(isRun){

                if(surfaceHolder.getSurface().isValid()){
                    if(isR==1){
                        isR=0;
                        ss=0;
                        initView();
                    }
                    mine=surfaceHolder.lockCanvas();
                    mine.drawBitmap(backG,0.0f,0.0f,paint);
                   /* paint.setTextSize(50);
                    paint.setARGB(255,255,255,0);
                    mine.drawText(""+SS,100,100,paint);*/

                   if(isD==1){
                       isD=0;

                       if(ccx[Dx][Dy]==0){
                           paint.setARGB(255,100,100,100);
                           backC.drawRect(new Rect(Dx*l1+l1+3,Dy*l1+l1+3,Dx*l1+2*l1-3,Dy*l1+2*l1-3),paint);
                           if(bbx[Dx][Dy]!=0){
                               paint.setTextSize(50);
                               paint.setARGB(255,0,0,255);
                               paint.setTextAlign(Paint.Align.CENTER);
                               backC.drawText(""+bbx[Dx][Dy],Dx*l1+3*l1/2,Dy*l1+3*l1/2+18,paint);
                               ddx[Dx][Dy]=1;
                           }

                           if(bbx[Dx][Dy]==0){
                               drawn(Dx,Dy);
                           }
                           int k,j;
                           ss=0;
                           for(k=0;k<m1;k++){
                               for(j=0;j<m2;j++){
                                   ss+=ddx[k][j];
                               }
                           }
                           if(ss==m1*m2-number){
                               paint.setTextSize(300);
                               paint.setARGB(120,255,255,0);
                               backC.drawText("你赢了！",800,500,paint);
                           }
                       }else{

                           backC.drawBitmap(mi,new Rect(0,0,640,640),new Rect(Dx*l1+l1+3,Dy*l1+l1+3,Dx*l1+2*l1-3,Dy*l1+2*l1-3),paint);
                           int k,j;
                           for(k=0;k<m1;k++){
                               for(j=0;j<m2;j++){
                                   if(ccx[k][j]==0){
                                       paint.setARGB(255,100,100,100);
                                       backC.drawRect(new Rect(k*l1+l1+3,j*l1+l1+3,k*l1+2*l1-3,j*l1+2*l1-3),paint);

                                       if(bbx[k][j]!=0){
                                           paint.setTextSize(50);
                                           paint.setARGB(255,0,0,255);
                                           paint.setTextAlign(Paint.Align.CENTER);
                                           backC.drawText(""+bbx[k][j],k*l1+3*l1/2,j*l1+3*l1/2+18,paint);
                                       }

                                   }else{
                                       backC.drawBitmap(mi,new Rect(0,0,640,640),new Rect(k*l1+l1+3,j*l1+l1+3,k*l1+2*l1-3,j*l1+2*l1-3),paint);

                                   }
                               }
                           }
                           paint.setTextSize(300);
                           paint.setARGB(120,0,255,255);
                           backC.drawText("你输了！",800,500,paint);


                       }


                   }
                    surfaceHolder.unlockCanvasAndPost(mine);
                }
            }

        }

    }

}
