package com.karim.converter;

import com.karim.converter.utils.Converter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterApplication {

    private static final Logger logger = LoggerFactory.getLogger(ConverterApplication.class);

    /**
     * Method for starting the Application.
     *
     * @param args required arguments
     */
    public static void startApplication(String[] args) {
        logger.info("Start application");
        CommandLine cmd = parseArgs(args);
        String workDirectory = cmd.getOptionValue("workdir");
        String fileName = cmd.getOptionValue("filename");
        new Converter().convertFromCvsToAvro(workDirectory, fileName);
    }

    private static CommandLine parseArgs(String[] args) {
        Options options = new Options();

        Option filename = new Option("filename", true, "file name");
        filename.setRequired(true);
        options.addOption(filename);

        Option workdir = new Option("workdir", true, "work directory");
        workdir.setRequired(true);
        options.addOption(workdir);


        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        return cmd;
    }

}
