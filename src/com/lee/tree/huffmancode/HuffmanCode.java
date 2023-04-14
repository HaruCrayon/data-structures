package com.lee.tree.huffmancode;

import java.io.*;
import java.util.*;

/**
 * @author LiJing
 * @version 1.0
 * 赫夫曼编码
 */
public class HuffmanCode {
    public static void main(String[] args) {
        /*
//        String content = "i like like like java do you like a java";
        String content = "hello world~";
        //得到原始的字符串对应的byte[]
        byte[] contentBytes = content.getBytes();//len=40
//        System.out.println(contentBytes.length);
        //压缩
        byte[] huffmanCodeBytes = huffmanZip(contentBytes);//len=17
//        System.out.println(Arrays.toString(huffmanCodeBytes) + "\nlen=" + huffmanCodeBytes.length);

        //解压
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodeBytes);
        System.out.println(Arrays.toString(sourceBytes));
        System.out.println(new String(sourceBytes));

         */

        //测试压缩文件
//        String srcFile = "E:\\Uninstall.xml";
//        String dstFile = "E:\\Uninstall.zip";
//
//        zipFile(srcFile, dstFile);
//        System.out.println("压缩文件ok~~");

        //测试解压文件
        String zipFile = "E:\\Uninstall.zip";
        String dstFile = "E:\\Uninstall2.xml";
        unzipFile(zipFile, dstFile);
        System.out.println("解压文件ok~~");
    }


    /**
     * 解压文件
     *
     * @param zipFile 准备解压的文件路径
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unzipFile(String zipFile, String dstFile) {
        FileInputStream is = null;
        ObjectInputStream ois = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(zipFile);
            ois = new ObjectInputStream(is);
            byte[] huffmanBytes = (byte[]) ois.readObject();
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            byte[] srcBytes = decode(huffmanCodes, huffmanBytes);
            os = new FileOutputStream(dstFile);
            os.write(srcBytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }


    /**
     * 压缩文件
     *
     * @param srcFile 希望压缩的文件的全路径
     * @param dstFile 压缩后将压缩文件放到哪个路径
     */
    public static void zipFile(String srcFile, String dstFile) {
        FileInputStream is = null;
        FileOutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            //创建文件输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件大小一样的字节数组
            byte[] srcBytes = new byte[is.available()];
            //读取文件
            is.read(srcBytes);
            //直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(srcBytes);
            //创建文件输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的对象输出流
            oos = new ObjectOutputStream(os);
            //把赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            //把赫夫曼编码写入压缩文件。这里以对象流的方式写入赫夫曼编码，是为了以后恢复源文件时使用
            oos.writeObject(huffmanCodes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                oos.close();
                os.close();
                is.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    /**
     * 数据解压 (使用赫夫曼编码解码)
     *
     * @param huffmanCodes 赫夫曼编码表
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 原始的字符串对应的字节数组
     */
    public static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        //先得到 huffmanBytes 对应的二进制的字符串，形式 "1010100010111..."
        StringBuilder stringBuilder = new StringBuilder();
        int subSize = 8;
        for (int i = 0; i < huffmanBytes.length - 1; i++) {
            byte b = huffmanBytes[i];
            //判断是不是最后一个字节
            if (i == huffmanBytes.length - 2) {
                subSize = huffmanBytes[huffmanBytes.length - 1];
            }
            stringBuilder.append(byteToBinaryString(subSize, b));
        }

        //把字符串按照指定的赫夫曼编码表进行解码
        //把赫夫曼编码表进行调换，因为反向查询 a->100 100->a
        HashMap<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        //创建一个集合，存放byte
        List<Byte> list = new ArrayList<>();
        //i 可以理解成就是索引，扫描 stringBuilder
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte b = null;

            while (flag) {
                //1010100010111...
                String key = stringBuilder.substring(i, i + count);//i不动，让count移动
                b = map.get(key);
                if (b == null) {//说明没有匹配到
                    count++;
                } else {
                    //匹配到了
                    flag = false;
                }
            }
            list.add(b);
            i += count;//i 直接移动到 i+count
        }
        //当for循环结束后，list中就存放了所有的字符  "i like like like java do you like a java"
        //把list中的数据放入到byte[] 并返回
        byte[] sourceBytes = new byte[list.size()];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = list.get(i);
        }
        return sourceBytes;

    }


    /**
     * 将一个 byte 转成一个二进制的字符串
     *
     * @param subSize 二进制字符串的位数
     * @param b       传入的 byte
     * @return 二进制的字符串
     */
    public static String byteToBinaryString(int subSize, byte b) {
        int temp = b;
        //如果是正数，则需要补高位
        temp |= 256;
        String str = Integer.toBinaryString(temp);//返回的是temp对应的二进制的补码，32位

        return str.substring(str.length() - subSize);
    }


    /**
     * 将前面的方法封装起来，便于调用
     *
     * @param bytes 原始的字符串对应的 byte[]
     * @return 返回经过赫夫曼编码处理后的 byte[] (压缩后的数组)
     * （注意：该字节数组最后一个元素是无效数据，用来存放编码数据最后一个byte有几位）
     */
    public static byte[] huffmanZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);
        //通过 List<Node> 创建对应的赫夫曼树
        Node root = createHuffmanTree(nodes);
//        preOrder(root);
        //生成赫夫曼树对应的赫夫曼编码
        Map<Byte, String> huffmanCodes = getCodes(root);
//        System.out.println("huffmanCodes= " + huffmanCodes);
        //根据生成的赫夫曼编码，得到压缩后的赫夫曼编码字节数组
        byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    /**
     * 功能：将字符串对应的byte[]数组，通过生成的赫夫曼编码表，得到一个赫夫曼编码压缩后的byte[]
     *
     * @param bytes        原始的字符串对应的 byte[]
     * @param huffmanCodes 生成的赫夫曼编码表 map
     * @return 返回赫夫曼编码处理后的 byte[]
     * （注意：该字节数组最后一个元素是无效数据，用来存放编码数据最后一个byte有几位）
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //通过 huffmanCodes 将 bytes 转成 赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        //将 赫夫曼编码对应的字符串"1010100010111111110..." 转成 byte[]
        //统计 byte[] huffmanCodeBytes 长度len
        //一句话 int len = (stringBuilder.length() + 7) / 8;
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8 + 1;//byte[len-1]存放最后一个byte有几位
        } else {
            len = stringBuilder.length() / 8 + 2;
        }
        //创建 存储压缩后的byte[]数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;//记录是第几个byte
        int lastSize = 8;//记录最后一个byte有几位
        for (int i = 0; i < stringBuilder.length(); i += 8) { //因为是每8位对应一个byte，所以步长 +8
            String strByte;
            if (i + 8 > stringBuilder.length()) {//不够8位
                strByte = stringBuilder.substring(i);
                lastSize = stringBuilder.length() % 8;
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            //将strByte 转成一个byte，放入到 huffmanCodeBytes
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }
        huffmanCodeBytes[len - 1] = (byte) lastSize;
        return huffmanCodeBytes;
    }


    //生成赫夫曼树对应的赫夫曼编码
    //思路:
    //1. 将赫夫曼编码表存放在 Map<Byte,String>
    //   生成的赫夫曼编码表{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    //2. 在生成赫夫曼编码表时，需要去拼接路径，定义一个StringBuilder 存储某个叶子结点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    //为了调用方便，重载 getCodes
    public static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        //处理root的左子树
        getCodes(root.left, "0", stringBuilder);
        //处理root的右子树
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }


    /**
     * 功能：将传入的 Node结点的所有叶子结点的赫夫曼编码得到，并放入到 huffmanCodes集合
     *
     * @param node          传入结点
     * @param code          路径：左子结点是 0，右子结点 1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code 加入到 stringBuilder2
        stringBuilder2.append(code);
        if (node != null) { //如果node == null不处理
            //判断当前node 是叶子结点还是非叶子结点
            if (node.data == null) { //非叶子结点
                //递归处理
                //向左递归
                getCodes(node.left, "0", stringBuilder2);
                //向右递归
                getCodes(node.right, "1", stringBuilder2);
            } else { //叶子结点
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }


    //前序遍历
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("是空树，不能遍历~~");
        }
    }


    //通过 List<Node> 创建对应的赫夫曼树
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);

            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;

            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);
        }
        //返回创建好的赫夫曼树的根结点
        return nodes.get(0);
    }


    //将准备构建赫夫曼树的Node节点放到List
    //体现 d:1 y:1 u:1 j:2  v:2  o:2  l:4  k:4  e:4 i:5  a:5   :9
    public static List<Node> getNodes(byte[] bytes) {
        ArrayList<Node> nodes = new ArrayList<>();
        //遍历bytes，统计 每一个byte出现的次数 -> map[key,value]
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }
        //遍历map，把每一个键值对转成一个Node对象，并加入到nodes集合
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }
}


//定义 Node { data(存放数据), weight(权值), left, right }
class Node implements Comparable<Node> {
    Byte data;
    int weight;
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}