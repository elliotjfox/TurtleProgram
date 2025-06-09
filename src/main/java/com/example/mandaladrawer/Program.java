package com.example.mandaladrawer;

import com.example.mandaladrawer.instruction.Instruction;
import com.example.mandaladrawer.parser.ProgramVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {

    public static String SQUARE_EXAMPLE = """
            forward 100
            rotate 90
            forward 100
            rotate 90
            forward 100
            rotate 90
            forward 100
            rotate 90
            """;

    public static String HEXAGON_EXAMPLE = """
            forward 60
            rotate 60
            forward 60
            rotate 60
            forward 60
            rotate 60
            forward 60
            rotate 60
            forward 60
            rotate 60
            forward 60
            rotate 60
            """;

    public static String FLOWER_EXAMPLE = """
            repeat 8 {
            	forward 100
            	rotate 22.5
            	repeat 180 {
            		forward 0.66
            		rotate 1
            	}
            	rotate 22.5
            	forward 100
            	rotate 180
            }
            """;

    public static String SPIRAL_EXAMPLE = """
            var length 1
            
            repeat 90 {
            	forward length
            	rotate 8
            	set length length + 0.125
            }
            """;

    public static String POLYGON_EXAMPLE = """
            var sides 6
            var length 50
            var angle 360 / sides
            
            repeat sides {
            	forward length
            	rotate angle
            }
            """;

    public static String DASHED_SQUARE_EXAMPLE = """
            penup
            goto -100 | -100
            pendown
            
            repeat 4 {
            repeat 10 {
                forward 10
                penup
                forward 10
                pendown
            }
            rotate 90
            }

            """;

    public static String CONCENTRIC_POLYGONS = """
            var length 100
            var sides 3
            
            penup
            goto length / -2 | -175
            pendown
            
            repeat 10 {
            	var angle 360 / sides
            
            	repeat sides {
            		forward length
            		rotate angle
            	}
            
            	set sides sides + 1
            }
            """;

    private final List<Instruction> instructions;
    private final Map<String, ProgramVariable> variableMap;

    public Program(Instruction... instructions) {
        this.instructions = new ArrayList<>(List.of(instructions));
        this.variableMap = new HashMap<>();
    }

    public void addInstructions(Instruction... instructions) {
        this.instructions.addAll(List.of(instructions));
    }

    public void addInstructions(Program program) {
        this.instructions.addAll(program.instructions);
    }

    public void addVariable(ProgramVariable variable) {
        variableMap.put(variable.getName(), variable);
    }

    public ProgramVariable getVariable(String variableName) {
        return variableMap.get(variableName);
    }

    public Instruction getNextInstruction() {
        return instructions.removeFirst();
    }

    public boolean hasInstructionsLeft() {
        return !instructions.isEmpty();
    }

    public int getInstructionSize() {
        return instructions.size();
    }
}
