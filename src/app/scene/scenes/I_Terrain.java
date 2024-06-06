package app.scene.scenes;

import app.rendering.buffer.VertexBufferObjectLayout;

import static org.lwjgl.stb.STBPerlin.stb_perlin_fbm_noise3;

public class I_Terrain extends H_IndividualFacesScene {

    private static final int CHUNK_SIZE = 50;
    private static final int CHUNK_SIZE_MINUS_ONE = CHUNK_SIZE - 1;
    private static final int BLOCK_AIR = 0;
    private static final int BLOCK_GRASS = 1;

    private char[] chunkBlocks;

    private void constructTerrainMesh() {
        chunkBlocks = new char[CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    float noiseValue = stb_perlin_fbm_noise3(x, y, z, 0.1f, 0.5f, 2);
                    if (noiseValue > 0.14f) {
                        chunkBlocks[z * CHUNK_SIZE * CHUNK_SIZE + y * CHUNK_SIZE + x] = BLOCK_GRASS;
                    } else {
                        chunkBlocks[z * CHUNK_SIZE * CHUNK_SIZE + y * CHUNK_SIZE + x] = BLOCK_AIR;
                    }
                }
            }
        }
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    char blockType = getBlockType(x, y, z);
                    if (blockType == BLOCK_AIR) {
                        continue;
                    }
                    if (y == 0) {
                        addFace(x, y, z, FACE_BOTTOM);
                        if (getBlockType(x, y + 1, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_TOP);
                    } else if (y == CHUNK_SIZE_MINUS_ONE) {
                        addFace(x, y, z, FACE_TOP);
                        if (getBlockType(x, y - 1, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_BOTTOM);
                    } else {
                        if (getBlockType(x, y + 1, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_TOP);
                        if (getBlockType(x, y - 1, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_BOTTOM);
                    }
                    if (x == CHUNK_SIZE_MINUS_ONE) {
                        addFace(x, y, z, FACE_RIGHT);
                        if (getBlockType(x - 1, y, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_LEFT);
                    } else if (x == 0) {
                        addFace(x, y, z, FACE_LEFT);
                        if (getBlockType(x + 1, y, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_RIGHT);
                    } else {
                        if (getBlockType(x + 1, y, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_RIGHT);
                        if (getBlockType(x - 1, y, z) == BLOCK_AIR)
                            addFace(x, y, z, FACE_LEFT);
                    }
                    if (z == CHUNK_SIZE_MINUS_ONE) {
                        addFace(x, y, z, FACE_FRONT);
                        if (getBlockType(x, y, z - 1) == BLOCK_AIR)
                            addFace(x, y, z, FACE_BACK);
                    } else if (z == 0) {
                        addFace(x, y, z, FACE_BACK);
                        if (getBlockType(x, y, z + 1) == BLOCK_AIR)
                            addFace(x, y, z, FACE_FRONT);
                    } else {
                        if (getBlockType(x, y, z + 1) == BLOCK_AIR)
                            addFace(x, y, z, FACE_FRONT);
                        if (getBlockType(x, y, z - 1) == BLOCK_AIR)
                            addFace(x, y, z, FACE_BACK);
                    }
                }
            }
        }
    }

    private char getBlockType(int x, int y, int z) {
        return chunkBlocks[z * CHUNK_SIZE * CHUNK_SIZE + y * CHUNK_SIZE + x];
    }

    public I_Terrain() {
        data = new float[0];
        layout = new VertexBufferObjectLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);
        layout.pushFloat(1);
        constructTerrainMesh();
        vertexCount = data.length / 6;
    }
}
