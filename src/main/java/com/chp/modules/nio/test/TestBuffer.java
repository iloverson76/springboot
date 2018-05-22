package com.chp.modules.nio.test;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 1.缓冲区（Buffer）：再java NIO中负责数据的存取，缓冲区就是数组，用于存储不同类型的数据
 * 
 * 根据数据类型不同（Boolean除外），提供了相应类型的缓冲区
 * 
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntegerBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 
 * 上述缓冲区的管理方式一致：通过allocate(int capacity)获取缓冲区
 * 
 * 2.缓冲区存取数据的两个核心方法：
 * put():存
 * get():取
 * 
 * 3.缓冲区的4个核心属性：
 * 1)capacity:容量，表示缓冲区中最大的存储数组的容量，一旦声明不可改变
 * 2)limit:界限，表示缓冲区中可以操作数据的大小，limit后的数据不能进行读写
 *3)position:位置，表示缓冲区中正在操作数据的位置
 *4):mark：标记，表示记录当前position的位置，可以通过reset()恢复到mark的位置
 * 0<=mark<=position<=limit<=capacity
 *
 *4:直接缓冲区与非直接缓冲区（区别的原理看视频的PPT）
 *直接缓冲区：通过allocate()方法直接分配，将缓冲区建立在jvm内存中
 *非直接缓冲区：通过allocateDirect()方法将缓冲区建立在操作系统的物理内存中，可以提高效率
 *(缺点：不安全，耗费资源，不易控制物理内存中的文件)
 */
public class TestBuffer {
	
	@Test
	public void PutGetFlip() {
		//1:分配一个指定大小的缓冲区
		System.out.println("============allocate()=================");
		ByteBuffer buf=ByteBuffer.allocate(1024);
		System.out.println(buf.position());//0
		System.out.println(buf.limit());//1024
		System.out.println(buf.capacity());//1024
		
		//2:利用put()写入数据到缓冲区中
		System.out.println("============put()=================");
		String str="abcde";
		buf.put(str.getBytes());
		System.out.println(buf.position());//5
		System.out.println(buf.limit());//1024
		System.out.println(buf.capacity());//1024
		
		//3.通过flip()方法切换到读取数据的模式
		System.out.println("============flip()=================");
		buf.flip();
		System.out.println(buf.position());//0
		System.out.println(buf.limit());//5
		System.out.println(buf.capacity());//1024
		
		//4.通过get()方法读取缓冲区的数据
		System.out.println("============get()=================");
		byte[] dst=new byte[buf.limit()];
		buf.get(dst);
		System.out.println(new String(dst,0,dst.length));
		System.out.println(buf.position());//5
		System.out.println(buf.limit());//5
		System.out.println(buf.capacity());//1024
		
		//5.rewind()：可重复读数据
		System.out.println("============rewind()=================");
		buf.rewind();
		System.out.println(buf.position());//0
		System.out.println(buf.limit());//5
		System.out.println(buf.capacity());//1024
		
		//6.clear():清空缓冲区,但是缓冲区中的数据依然存在，
		//只是数据处于“被遗忘”状态，3个属性都回到了最初状态，不能正确地读取数据
		System.out.println("============clear()=================");
		buf.clear();
		System.out.println(buf.position());//0
		System.out.println(buf.limit());//0
		System.out.println(buf.capacity());//1024
		System.out.println((char)buf.get());
	}
	
	@Test
	public void MarkResetTest() {
		String str="abcde";
		ByteBuffer buf=ByteBuffer.allocate(1024);
		buf.put(str.getBytes());
		buf.flip();
		byte[] dst=new byte[buf.limit()];
		buf.get(dst, 0,2);
		System.out.println(new String(dst,0,2));//ab
		
		System.out.println(buf.position());//2
		System.out.println(buf.limit());//5
		System.out.println(buf.capacity());//1024
		
		System.out.println("------------mark()标记-------------------");
		buf.mark();
		buf.get(dst, 2, 2);
		System.out.println(new String(dst,2,2));//cd
		System.out.println(buf.position());//4
		System.out.println(buf.limit());//5
		System.out.println(buf.capacity());//1024
		
		System.out.println("------------reset()-------------------");
		buf.reset();//恢复到mark的位置
		System.out.println(buf.position());//2
		System.out.println(buf.limit());//5
		System.out.println(buf.capacity());//1024
		
		System.out.println("------------hasRemaining()-------------------");
		if(buf.hasRemaining()) {//缓冲区是否还有数据
			//缓冲区当前的position~limit区间的元素数量
			//获取缓冲区中可以操作的数量
			System.out.println(buf.remaining());//3
		}
	}
	
	@Test
	public void allocateDirectTest() {
		//创建非直接缓冲区
		ByteBuffer buf=ByteBuffer.allocateDirect(1024);
		System.out.println(buf.isDirect());//true
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
