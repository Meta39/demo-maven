package com.fu.frame;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.UUID;

@Component
public class MainWin {
    private static final Logger log = LoggerFactory.getLogger(MainWin.class);

    public void init() {
        JFrame jFrame = new JFrame("文件转换（转换后的文件在桌面）");//定义主容器

        JPanel jPanel = new JPanel();//定义面板容器

        JLabel jLabel = new JLabel("请选择转换类型：");//定义只读文本框
        jPanel.add(jLabel);

        String[] constellations = {"pdf", "docx", "doc"};
        JComboBox<String> jComboBox = new JComboBox<>(constellations);//下拉框
        jPanel.add(jComboBox);//把数据填充到下拉框
        jFrame.add(jPanel);//把下拉框放入容器内

        JButton jButton = new JButton("上传文件");
        //添加按钮鼠标点击事件
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String selectedItem = (String) jComboBox.getSelectedItem();
                eventOnImport(jButton, selectedItem);
            }
        });
        jPanel.add(jButton);

        //显示窗体要放到最后面，否则可能会无法显示面板容器
        jFrame.setVisible(true);//显示窗体
        jFrame.setBounds(300, 250, 400, 300);//设置窗体显示位置和大小(x,y,width, height)
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体操作
    }

    /**
     * 文件上传
     */
    private static void eventOnImport(JButton jButton, String formatFileType) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        int returnVal = chooser.showOpenDialog(jButton);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //得到选择的文件
            File[] arrfiles = chooser.getSelectedFiles();
            if (arrfiles == null || arrfiles.length == 0) {
                return;
            }
            String path = System.getProperty("user.home") + File.separator + "Desktop";
            for (File f : arrfiles) {
                String newFileName = UUID.randomUUID() + "." + formatFileType;
                File des = new File(path, newFileName);
                try (FileOutputStream out = new FileOutputStream(des)) {
                    //获取上传的文件流
                    Document doc = new Document(Files.newInputStream(f.toPath()));
                    doc.save(out, SaveFormat.fromName(formatFileType.toUpperCase()));
                } catch (Exception e) {
                    log.error("转换文件失败", e);
                    JOptionPane.showMessageDialog(null, "转换文件失败", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(null, "上传成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
