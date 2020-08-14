package my.project;

import my.project.model.Cell;
import my.project.model.LifeLogic;
import my.project.model.ResponseWithInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RestController
public class RestControllerClass {
    @Autowired
    private LifeLogic lifeLogic;

    @PostMapping("/click")
    public ResponseEntity<ResponseWithInfo> catchLeftClick(@RequestBody Cell cell) {
        if (lifeLogic.getCell(cell) == 0) {
            lifeLogic.setCell(cell);
        } else {
            lifeLogic.removeCell(cell);
        }
        return new ResponseEntity<>(prepareResponse(), HttpStatus.OK);
    }

    @PostMapping("/move")
    public ResponseEntity<ResponseWithInfo> catchMove(@RequestBody Cell cell) {
        lifeLogic.moveAll(cell.getX(), cell.getY());
        return new ResponseEntity<>(prepareResponse(), HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<ResponseWithInfo> catchLoad(@RequestParam("file") MultipartFile file){
        lifeLogic.clearField();
        try (InputStream is = new ByteArrayInputStream(file.getBytes());
             LineNumberReader fileReader = new LineNumberReader(new InputStreamReader(is))){
            String str;
            int startX = 0;
            int startY = 0;
            int lineNumber = -1;
            while ((str = fileReader.readLine()) != null) {
                lineNumber++;
                if (str.startsWith("#P")) {
                    String[] coordinates = str.split(" ");
                    startX = Integer.parseInt(coordinates[1]);
                    startY = Integer.parseInt(coordinates[2]);
                    lineNumber = -1;
                } else if (!str.startsWith("#")) {
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == '*') {
                            lifeLogic.setCell(startX + i, startY + lineNumber);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(prepareResponse(), HttpStatus.OK);
    }

    @GetMapping("/getData")
    public ResponseEntity<ResponseWithInfo> catchGetData() {
        return new ResponseEntity<>(prepareResponse(), HttpStatus.OK);
    }

    @GetMapping("/step")
    public ResponseEntity<ResponseWithInfo> catchStep() {
        lifeLogic.next();
        return new ResponseEntity<>(prepareResponse(), HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<ResponseWithInfo> catchClear() {
        lifeLogic.clearField();
        return new ResponseEntity<>(prepareResponse(), HttpStatus.OK);
    }

    @GetMapping("/save")
    public ResponseEntity<Object> saveAsFile() throws IOException  {
        InputStream is = new ByteArrayInputStream(lifeLogic.getPattern().getBytes());
        InputStreamResource resource = new InputStreamResource(is);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"save.lif\"");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(
                is.available()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
    }

    private ResponseWithInfo prepareResponse() {
        int generation = lifeLogic.getCountPopulation();
        int liveCells = lifeLogic.getLiveCells();
        int isGameOver = lifeLogic.isGameOver()?1:0;
        Object [] cells = lifeLogic.getCells().toArray();
        return new ResponseWithInfo(cells, generation, liveCells, isGameOver);
    }
}
