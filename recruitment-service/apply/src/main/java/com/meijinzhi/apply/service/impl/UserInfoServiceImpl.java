package com.meijinzhi.apply.service.impl;

//import com.itextpdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.meijinzhi.apply.dao.ResumeInfoMapper;
import com.meijinzhi.apply.dao.UserInfoMapper;
import com.meijinzhi.apply.dao.WorkExperienceMapper;
import com.meijinzhi.apply.entity.ResumeInfo;
import com.meijinzhi.apply.entity.UserInfo;
import com.meijinzhi.apply.entity.WorkExperience;
import com.meijinzhi.apply.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    ResumeInfoMapper resumeInfoMapper;
    @Autowired
    WorkExperienceMapper workExperienceMapper;
    private static long id;
    private static long userId;

    @Override
    public Boolean signIn(UserInfo userInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfoMapper.signIn(userInfo.getUsername(), passwordEncoder.encode(userInfo.getPassword()), userInfo.getEmail());
        return true;
    }

    @Override
    public Boolean LogIn(UserInfo userInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserInfo signedUser = userInfoMapper.selectByUsername(userInfo.getUsername());
        if (signedUser != null) {
            return passwordEncoder.matches(userInfo.getPassword(), signedUser.getPassword());
        } else {
            return false;
        }
    }

    @Override
    public Boolean forget(UserInfo userInfo) {
        UserInfo signedUser = userInfoMapper.selectByUsername(userInfo.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (signedUser.getEmail().equals(userInfo.getEmail())) {
            userInfoMapper.updatePassword(userInfo.getUsername(), passwordEncoder.encode(userInfo.getPassword()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setResume(ResumeInfo resumeInfo, String[] companyName, String[] startTime, String[] endTime, String[] workDescribe, String username) {
        try {
            long userId = userInfoMapper.selectByUsername(username).getId();
            List<WorkExperience> workExperiences = workExperienceMapper.selectByUserId(userId);
            ResumeInfo resumeInfoGet = resumeInfoMapper.selectByUserId(userId);
            if (resumeInfoGet != null) {
                resumeInfo.setId(resumeInfoGet.getId());
                resumeInfo.setUserId(userId);
                resumeInfoMapper.updateById(resumeInfo);
            } else {
                resumeInfo.setUserId(resumeInfoGet.getId());
                resumeInfoMapper.insert(resumeInfo);
            }
            if (workExperiences.size() == 0) {
                WorkExperience workExperience = new WorkExperience();
                for (int i = 0; i < companyName.length; i++) {
                    workExperience.setUserId(userId);
                    workExperience.setStartTime(startTime[i]);
                    workExperience.setEndTime(endTime[i]);
                    workExperience.setCompanyName(companyName[i]);
                    workExperience.setWorkDescribe(workDescribe[i]);
                    workExperienceMapper.insert(workExperience);
                }
            } else {
                if (workExperiences.size() == companyName.length) {
                    int i = 0;
                    for (WorkExperience workExperience : workExperiences) {
                        workExperience.setUserId(userId);
                        workExperience.setStartTime(startTime[i]);
                        workExperience.setEndTime(endTime[i]);
                        workExperience.setCompanyName(companyName[i]);
                        workExperience.setWorkDescribe(workDescribe[i]);
                        workExperienceMapper.updateById(workExperience);
                        i++;
                    }
                } else if (workExperiences.size() < companyName.length) {
//                    int difference = companyName.length- workExperiences.size();
                    int i = 0;
                    for (WorkExperience workExperience : workExperiences) {
                        workExperience.setUserId(userId);
                        workExperience.setStartTime(startTime[i]);
                        workExperience.setEndTime(endTime[i]);
                        workExperience.setCompanyName(companyName[i]);
                        workExperience.setWorkDescribe(workDescribe[i]);
                        workExperienceMapper.updateById(workExperience);
                        i++;
                    }
                    WorkExperience workExperience = new WorkExperience();
                    for (int j = workExperiences.size(); j < companyName.length; j++) {
                        workExperience.setUserId(userId);
                        workExperience.setStartTime(startTime[j]);
                        workExperience.setEndTime(endTime[j]);
                        workExperience.setCompanyName(companyName[j]);
                        workExperience.setWorkDescribe(workDescribe[j]);
                        workExperienceMapper.insert(workExperience);
                    }
                } else {
                    List<WorkExperience> workExperiencesSub = workExperiences.subList(0, companyName.length);
                    int i = 0;
                    for (WorkExperience workExperience : workExperiencesSub) {
                        workExperience.setUserId(userId);
                        workExperience.setStartTime(startTime[i]);
                        workExperience.setEndTime(endTime[i]);
                        workExperience.setCompanyName(companyName[i]);
                        workExperience.setWorkDescribe(workDescribe[i]);
                        workExperienceMapper.updateById(workExperience);
                        i++;
                    }
                    List<WorkExperience> workExperiencesResidue = workExperiences.subList(companyName.length, workExperiences.size());
                    WorkExperience workExperience = new WorkExperience();
                    for (int j = 0; j < workExperiencesResidue.size(); j++) {
                        workExperience = workExperiencesResidue.get(j);
                        workExperienceMapper.deleteById(workExperience.getId());
                    }
                }

            }
//            FileOutputStream fileOutputStream = new FileOutputStream("D:\\pdf\\test.pdf");
//            Document document=new Document();
//            PdfWriter writer = PdfWriter.getInstance(document,fileOutputStream);
//            document.open();
//            document.add(new Paragraph(resumeInfo.getName()));
//            document.add(new Paragraph(resumeInfo.getSex()));
//            document.add(new Paragraph(resumeInfo.getTelephone()));
//            document.add(new Paragraph(resumeInfo.getEmail()));
//            document.close();
//            fileOutputStream.flush();
            ResumeInfo resumeInfoAfter = resumeInfoMapper.selectByUserId(userId);
            this.id = resumeInfoAfter.getId();
            this.userId = userId;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateTempPDF(ResumeInfo resumeInfo, List<WorkExperience> workExperiences) throws Exception {
        PdfReader reader = null;
        PdfStamper ps = null;
        OutputStream fos = null;
        ByteArrayOutputStream bos = null;
        try {
            String fileName = "D:\\recruitment\\recruitment-service\\apply\\src\\main\\resources\\template\\templatePdf.pdf";//模板绝对路径
            reader = new PdfReader(fileName);
            bos = new ByteArrayOutputStream();
            ps = new PdfStamper(reader, bos);

            // 使用中文字体
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(bf);

            AcroFields fields = ps.getAcroFields();
            fields.setSubstitutionFonts(fontList);
            fillData(fields, data(resumeInfo, workExperiences));//渲染

            //必须要调用这个，否则文档不会生成的
            ps.setFormFlattening(true);
            if (ps != null) {
                ps.close();
            }
            //生成pdf路径存放的路径
            fos = new FileOutputStream("D:\\pdf\\test.pdf");
            fos.write(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.flush();
                fos.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static void fillData(AcroFields fields, Map<String, String> data) {
        try {
            for (String key : data.keySet()) {
                String value = data.get(key);
                // 为字段赋值,注意字段名称是区分大小写的
                fields.setField(key, value);

            }
//            fields.setField("picture","C:\\Users\\27299\\Desktop\\1.jpeg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充数据源
     * 其中data存放的key值与pdf模板中的文本域值相对应
     */
    public static Map<String, String> data(ResumeInfo resumeInfo, List<WorkExperience> workExperiences) throws ParseException, IOException {
        Map<String, String> data = new HashMap<String, String>();
        //key要与模板中的别名一一对应
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();

        for (WorkExperience workExperience : workExperiences) {
            String startTime1 = workExperience.getStartTime();
            String endTime1 = workExperience.getEndTime();
            Date startTimeDate = sdf.parse(startTime1);
            Date endTimeDate = sdf.parse(endTime1);
            String startTime = df.format(startTimeDate);
            String endTime = df.format(endTimeDate);
            sb.append(workExperience.getCompanyName()).append("\r\n")
                    .append(startTime).append("-")
                    .append(endTime).append("\r\n")
                    .append(workExperience.getWorkDescribe()).append("\r\n");
        }
        String workExperience = sb.toString();
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(resumeInfo.getPhoto());
        data.put("name", resumeInfo.getName());
        data.put("sex", resumeInfo.getSex());
        data.put("telephone", resumeInfo.getTelephone());
        data.put("email", resumeInfo.getEmail());
        data.put("workExperience", workExperience);
        data.put("personDescribe", resumeInfo.getPersonalIntroduction());
//        data.put("图像1_af_image", resumeInfo.getPhoto());
        return data;
    }

    @Override
    public void updatePhoto(MultipartFile file) throws Exception {
//        BASE64Encoder encoder = new BASE64Encoder();
////        String photo = encoder.encode(file.getBytes());
//        String photo = "localhost:9000/recruitment/companyLogo/腾讯.jpg";
        ResumeInfo resumeInfo = new ResumeInfo();
        resumeInfo.setId(id);
//        resumeInfo.setPhoto(photo);
        resumeInfo.setUserId(userId);
        resumeInfoMapper.updateById(resumeInfo);
        List<WorkExperience> workExperience2 = workExperienceMapper.selectByUserId(userId);
        ResumeInfo resumeInfo1 = resumeInfoMapper.selectById(id);
//        generateTempPDF(resumeInfo1, workExperience2);
        picutre(workExperience2,resumeInfo1,file);
    }
    void picutre(List<WorkExperience> workExperiences,ResumeInfo resumeInfo,MultipartFile file) {
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            StringBuilder sb=new StringBuilder();
            for (WorkExperience workExperience : workExperiences) {
                String startTime1 = workExperience.getStartTime();
                String endTime1 = workExperience.getEndTime();
                Date startTimeDate = sdf.parse(startTime1);
                Date endTimeDate = sdf.parse(endTime1);
                String startTime = df.format(startTimeDate);
                String endTime = df.format(endTimeDate);
                sb.append(workExperience.getCompanyName()).append("\r\n")
                        .append("                         ").append(startTime).append("-")
                        .append(endTime).append("\r\n").append("                         ")
                        .append(workExperience.getWorkDescribe()).append("\r\n").append("\r\n").append("                         ");
            }
            String workExperience = sb.toString();
            Rectangle rectangle = new Rectangle(PageSize.A4);
            Document document = new Document(rectangle, 30 , 30, 30, 30);
            String pdfPath = "D:\\DevelopSoft\\apache-tomcat-8.5.78\\webapps\\recruitment\\resume\\"+resumeInfo.getUserId()+".pdf";   // PDF 的输出位置
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
//                PdfWriter writer = PdfWriter.getInstance(document, pdf);
            } catch (DocumentException e) {
                logger.error("将文档对象设置到文件输出流中 - 出错了！", e);
            }
            document.open();    // 打开文档对象
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font size20Font=new Font(baseFont,20,Font.NORMAL);
            Font size13Font=new Font(baseFont,13,Font.NORMAL);
            Image image = null;     // 声明图片对象
            try {
                image=Image.getInstance(file.getBytes());// 取得图片对象
            } catch (BadElementException | IOException e) {
                logger.error("实例化【图片】 - 失败！", e);

                return;
            }
//            Paragraph spacing =new Paragraph(80);
            Paragraph title=new Paragraph();
            Chunk titleChunk=new Chunk("煤气罐招聘在线简历",size20Font);
            title.add(titleChunk);
            title.setSpacingAfter(30);
            Paragraph name=new Paragraph();
            Chunk nameChunk=new Chunk("姓          名："+resumeInfo.getName(),size13Font);
            name.add(nameChunk);
            name.setSpacingAfter(20);
            Paragraph sex=new Paragraph();
            Chunk sexChunk=new Chunk("性          别："+resumeInfo.getSex(),size13Font);
            sex.add(sexChunk);
            sex.setSpacingAfter(20);
            Paragraph telephone=new Paragraph();
            Chunk telephoneChunk=new Chunk("电话号码："+resumeInfo.getTelephone(),size13Font);
            telephone.add(telephoneChunk);
            telephone.setSpacingAfter(20);
            Paragraph email=new Paragraph();
            Chunk emailChunk=new Chunk("邮          箱："+resumeInfo.getEmail(),size13Font);
            email.add(emailChunk);
            email.setSpacingAfter(20);
            Paragraph workExperiencePara=new Paragraph();
            Chunk workExperienceChunk=new Chunk("工作经历："+workExperience,size13Font);
            workExperiencePara.add(workExperienceChunk);
            workExperiencePara.setSpacingAfter(20);
            Paragraph introduce=new Paragraph();
            Chunk introduceChunk=new Chunk("个人简介："+resumeInfo.getPersonalIntroduction(),size13Font);
            introduce.add(introduceChunk);
            introduce.setSpacingAfter(20);
            image.scaleAbsolute(87.5f, 122.5f);
            image.setAbsolutePosition(460f, 670f);      // （以左下角为原点）设置图片的坐标
            try {
                document.add(image);
                document.add(title);
//                document.add(spacing);
                document.add(name);
                document.add(sex);
                document.add(telephone);
                document.add(email);
                document.add(workExperiencePara);
                document.add(introduce);

            } catch (DocumentException e) {
                logger.error("将图片对象加入到文档对象中时 - 出错了！", e);
            }

            document.close();       // 关闭文档
            UserInfo user=new UserInfo();
            user.setId(userId);
            user.setResume("http://192.168.10.111:9000/recruitment/resume/"+userId+".pdf");
//            encoder.encode(pdf);
            userInfoMapper.updateById(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
