package org.eclipse.emf.henshin.interpreter.giraph;

import java.util.*;

public class HenshinUtilTemplate
{
  protected static String nl;
  public static synchronized HenshinUtilTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    HenshinUtilTemplate result = new HenshinUtilTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * Licensed to the Apache Software Foundation (ASF) under one" + NL + " * or more contributor license agreements.  See the NOTICE file" + NL + " * distributed with this work for additional information" + NL + " * regarding copyright ownership.  The ASF licenses this file" + NL + " * to you under the Apache License, Version 2.0 (the" + NL + " * \"License\"); you may not use this file except in compliance" + NL + " * with the License.  You may obtain a copy of the License at" + NL + " *" + NL + " *     http://www.apache.org/licenses/LICENSE-2.0" + NL + " *" + NL + " * Unless required by applicable law or agreed to in writing, software" + NL + " * distributed under the License is distributed on an \"AS IS\" BASIS," + NL + " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." + NL + " * See the License for the specific language governing permissions and" + NL + " * limitations under the License." + NL + " */" + NL + "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.nio.ByteBuffer;" + NL + "import java.nio.IntBuffer;" + NL + "import java.nio.LongBuffer;" + NL + "import java.util.Arrays;" + NL + "import java.util.HashSet;" + NL + "import java.util.List;" + NL + "import java.util.Set;" + NL + "import java.util.UUID;" + NL + "" + NL + "import org.apache.giraph.aggregators.BasicAggregator;" + NL + "import org.apache.giraph.edge.Edge;" + NL + "import org.apache.giraph.edge.EdgeFactory;" + NL + "import org.apache.giraph.graph.Vertex;" + NL + "import org.apache.giraph.io.formats.TextVertexInputFormat;" + NL + "import org.apache.giraph.io.formats.TextVertexOutputFormat;" + NL + "import org.apache.hadoop.io.BytesWritable;" + NL + "import org.apache.hadoop.io.ByteWritable;" + NL + "import org.apache.hadoop.io.Text;" + NL + "import org.apache.hadoop.mapreduce.InputSplit;" + NL + "import org.apache.hadoop.mapreduce.TaskAttemptContext;" + NL + "import org.json.JSONArray;" + NL + "import org.json.JSONException;" + NL + "" + NL + "import com.google.common.collect.Lists;" + NL + "" + NL + "/**" + NL + " * Henshin utility classes and methods." + NL + " */" + NL + "public class HenshinUtil {" + NL + "" + NL + "  /**" + NL + "   * Length of integers in bytes." + NL + "   */" + NL + "  private static final int INT_LENGTH = Integer.SIZE / Byte.SIZE;" + NL + "" + NL + "  /**" + NL + "   * Private constructor." + NL + "   */" + NL + "  private HenshinUtil() {" + NL + "    // Prevent instantiation" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin data." + NL + "   */" + NL + "  public abstract static class Bytes extends BytesWritable {" + NL + "" + NL + "    /**" + NL + "     * Default constructor." + NL + "     */" + NL + "    public Bytes() {" + NL + "      super();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Extra constructor." + NL + "     * @param data The data." + NL + "     */" + NL + "    public Bytes(byte[] data) {" + NL + "      super(data);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Set the size." + NL + "     * @param size The new size." + NL + "     */" + NL + "    @Override" + NL + "    public void setSize(int size) {" + NL + "      if (size != getCapacity()) {" + NL + "        setCapacity(size);" + NL + "      }" + NL + "      super.setSize(size);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Pretty-print this bytes object." + NL + "     * @return The printed string." + NL + "     */" + NL + "    @Override" + NL + "    public String toString() {" + NL + "      byte[] bytes = getBytes();" + NL + "      StringBuffer result = new StringBuffer();" + NL + "      for (int i = 0; i < bytes.length; i++) {" + NL + "        result.append(bytes[i]);" + NL + "        if (i < bytes.length - 1) {" + NL + "          result.append(\",\");" + NL + "        }" + NL + "      }" + NL + "      return \"[\" + result + \"]\";" + NL + "    }" + NL + "" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin vertex ID." + NL + "   */" + NL + "  public static class VertexId extends Bytes {" + NL + "" + NL + "    /**" + NL + "     * Default constructor." + NL + "     */" + NL + "    public VertexId() {" + NL + "      super();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Extra constructor." + NL + "     * @param data The data." + NL + "     */" + NL + "    public VertexId(byte[] data) {" + NL + "      super(data);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Create a new random vertex ID." + NL + "     * The vertex ID is derived from a random UUID." + NL + "     * @return The new vertex ID." + NL + "     */" + NL + "    public static VertexId randomVertexId() {" + NL + "      UUID uuid = UUID.randomUUID();" + NL + "      byte[] bytes = new byte[(Long.SIZE / Byte.SIZE) * 2];" + NL + "      ByteBuffer buffer = ByteBuffer.wrap(bytes);" + NL + "      LongBuffer longBuffer = buffer.asLongBuffer();" + NL + "      longBuffer.put(new long[] {" + NL + "        uuid.getMostSignificantBits()," + NL + "        uuid.getLeastSignificantBits()" + NL + "      });" + NL + "      return new VertexId(bytes);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Create an extended version of this vertex ID." + NL + "     * @param value The value to be appended to this vertex ID." + NL + "     * @return The extended version of this vertex ID." + NL + "     */" + NL + "    public VertexId append(byte value) {" + NL + "      byte[] bytes = getBytes();" + NL + "      bytes = Arrays.copyOf(bytes, bytes.length + 1);" + NL + "      bytes[bytes.length - 1] = value;" + NL + "      return new VertexId(bytes);" + NL + "    }" + NL + "" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin match object." + NL + "   */" + NL + "  public static class Match extends Bytes {" + NL + "" + NL + "    /**" + NL + "     * Empty match." + NL + "     */" + NL + "    public static final Match EMPTY = new Match();" + NL + "" + NL + "    /**" + NL + "     * Default constructor." + NL + "     */" + NL + "    public Match() {" + NL + "      super();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Extra constructor." + NL + "     * @param data The data." + NL + "     */" + NL + "    public Match(byte[] data) {" + NL + "      super(data);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Get the vertex ID of a matched node." + NL + "     * @param vertexIndex Index of the next vertex." + NL + "     * @return The vertex ID." + NL + "     */" + NL + "    public VertexId getVertexId(int vertexIndex) {" + NL + "      byte[] bytes = getBytes();" + NL + "      int d = 0;" + NL + "      for (int i = 0; i < vertexIndex; i++) {" + NL + "        if (d >= bytes.length) {" + NL + "          return null;" + NL + "        }" + NL + "        d += bytes[d] + 1;" + NL + "      }" + NL + "      if (d >= bytes.length) {" + NL + "        return null;" + NL + "      }" + NL + "      return new VertexId(" + NL + "        Arrays.copyOfRange(bytes, d + 1, d + 1 + bytes[d]));" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Get the index of a vertex ID of a matched node." + NL + "     * @param vertexId A vertex ID." + NL + "     * @return The index of the vertex ID or -1." + NL + "     */" + NL + "    public int indexOf(VertexId vertexId) {" + NL + "      int i = 0;" + NL + "      VertexId id;" + NL + "      do {" + NL + "        id = getVertexId(i);" + NL + "        if (vertexId.equals(id)) {" + NL + "          return i;" + NL + "        }" + NL + "        i++;" + NL + "      } while (id != null);" + NL + "      return -1;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Returns true if this match is injective." + NL + "     * @return true if this match is injective." + NL + "     */" + NL + "    public boolean isInjective() {" + NL + "      Set<VertexId> ids = new HashSet<VertexId>();" + NL + "      int i = 0;" + NL + "      VertexId id;" + NL + "      do {" + NL + "        id = getVertexId(i++);" + NL + "        if (id != null && !ids.add(id)) {" + NL + "          return false;" + NL + "        }" + NL + "      } while (id != null);" + NL + "      return true;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Create an extended version of this (partial) match." + NL + "     * @param vertexId The ID of the next matched vertex." + NL + "     * @return The extended match object." + NL + "     */" + NL + "    public Match append(VertexId vertexId) {" + NL + "      byte[] bytes = getBytes();" + NL + "      byte[] id = vertexId.getBytes();" + NL + "      byte[] result = Arrays.copyOf(bytes, bytes.length + 1 + id.length);" + NL + "      result[bytes.length] = (byte) id.length;" + NL + "      System.arraycopy(id, 0, result, bytes.length + 1, id.length);" + NL + "      return new Match(result);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Create an extended version of this (partial) match." + NL + "     * @param match Another partial match for the next matched vertices." + NL + "     * @return The extended match object." + NL + "     */" + NL + "    public Match append(Match match) {" + NL + "      byte[] bytes1 = getBytes();" + NL + "      byte[] bytes2 = match.getBytes();" + NL + "      bytes1 = Arrays.copyOf(bytes1, bytes1.length + bytes2.length);" + NL + "      System.arraycopy(bytes2, 0," + NL + "        bytes1, bytes1.length - bytes2.length, bytes2.length);" + NL + "      return new Match(bytes1);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Remove a vertex ID of a matched node." + NL + "     * @param vertexIndex Index of the vertex ID." + NL + "     * @return The new match." + NL + "     */" + NL + "    public Match remove(int vertexIndex) {" + NL + "      byte[] bytes = getBytes();" + NL + "      int d = 0;" + NL + "      for (int i = 0; i < vertexIndex; i++) {" + NL + "        if (d >= bytes.length) {" + NL + "          return null;" + NL + "        }" + NL + "        d += bytes[d] + 1;" + NL + "      }" + NL + "      if (d >= bytes.length) {" + NL + "        return null;" + NL + "      }" + NL + "      byte[] result = Arrays.copyOf(bytes, bytes.length - bytes[d] - 1);" + NL + "      if (d < result.length) {" + NL + "        System.arraycopy(bytes, d + 1 + bytes[d]," + NL + "          result, d, result.length - d);" + NL + "      }" + NL + "      return new Match(result);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Pretty-print this match." + NL + "     * @return The printed string." + NL + "     */" + NL + "    @Override" + NL + "    public String toString() {" + NL + "      byte[] bytes = getBytes();" + NL + "      StringBuffer result = new StringBuffer();" + NL + "      int i = 0;" + NL + "      while (i < bytes.length) {" + NL + "        int len = bytes[i++];" + NL + "        result.append(\"[\");" + NL + "        for (int j = 0; j < len; j++) {" + NL + "          result.append(bytes[i + j]);" + NL + "          if (j < len - 1) {" + NL + "            result.append(\",\");" + NL + "          }" + NL + "        }" + NL + "        result.append(\"]\");" + NL + "        i += len;" + NL + "        if (i < bytes.length - 1) {" + NL + "          result.append(\",\");" + NL + "        }" + NL + "      }" + NL + "      return \"[\" + result + \"]\";" + NL + "    }" + NL + "" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin application stack." + NL + "   */" + NL + "  public static class ApplicationStack extends Bytes {" + NL + "" + NL + "    /**" + NL + "     * Default constructor." + NL + "     */" + NL + "    public ApplicationStack() {" + NL + "      super();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Extra constructor." + NL + "     * @param data The data." + NL + "     */" + NL + "    public ApplicationStack(byte[] data) {" + NL + "      super(data);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Get the size of this application stack." + NL + "     * @return the size of this application stack." + NL + "     */" + NL + "    public int getStackSize() {" + NL + "      return (getBytes().length / INT_LENGTH) / 2;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Get the unit index at an absolute position." + NL + "     * @param position An absolute position in the stack." + NL + "     * @return the unit index or -1." + NL + "     */" + NL + "    public int getUnit(int position) {" + NL + "      IntBuffer intBuf = ByteBuffer.wrap(getBytes()).asIntBuffer();" + NL + "      if (position < 0 || position * 2 >= intBuf.limit()) {" + NL + "        return -1;" + NL + "      }" + NL + "      return intBuf.get(position * 2);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Get the microstep at an absolute position." + NL + "     * @param position An absolute position in the stack." + NL + "     * @return the microstp or -1." + NL + "     */" + NL + "    public int getMicrostep(int position) {" + NL + "      IntBuffer intBuf = ByteBuffer.wrap(getBytes()).asIntBuffer();" + NL + "      if (position < 0 || (position * 2) + 1 >= intBuf.limit()) {" + NL + "        return -1;" + NL + "      }" + NL + "      return intBuf.get((position * 2) + 1);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Get the unit index at the last position." + NL + "     * @return the unit index or -1." + NL + "     */" + NL + "    public int getLastUnit() {" + NL + "      return getUnit(getStackSize() - 1);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Get the microstep at the last position." + NL + "     * @return the microstep or -1." + NL + "     */" + NL + "    public int getLastMicrostep() {" + NL + "      return getMicrostep(getStackSize() - 1);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Create an extended version of this application stack." + NL + "     * @param unit The new unit index." + NL + "     * @param microstep The new microstep." + NL + "     * @return The extended version of this application stack." + NL + "     */" + NL + "    public ApplicationStack append(int unit, int microstep) {" + NL + "      byte[] bytes = getBytes();" + NL + "      bytes = Arrays.copyOf(bytes, bytes.length + (INT_LENGTH * 2));" + NL + "      IntBuffer intBuffer = ByteBuffer.wrap(bytes).asIntBuffer();" + NL + "      intBuffer.put((bytes.length / INT_LENGTH) - 2, unit);" + NL + "      intBuffer.put((bytes.length / INT_LENGTH) - 1, microstep);" + NL + "      return new ApplicationStack(bytes);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Create a new version of this application stack without the last entry." + NL + "     * @return The new version of this application stack." + NL + "     */" + NL + "    public ApplicationStack removeLast() {" + NL + "      byte[] bytes = getBytes();" + NL;
  protected final String TEXT_3 = "      bytes = Arrays.copyOf(bytes," + NL + "        Math.max(0, bytes.length - (INT_LENGTH * 2)));" + NL + "      return new ApplicationStack(bytes);" + NL + "    }" + NL + "" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Aggregator class for application stacks." + NL + "   */" + NL + "  public static class ApplicationStackAggregator extends" + NL + "    BasicAggregator<ApplicationStack> {" + NL + "" + NL + "    @Override" + NL + "    public void aggregate(ApplicationStack stack) {" + NL + "      setAggregatedValue(stack);" + NL + "    }" + NL + "" + NL + "    @Override" + NL + "    public ApplicationStack createInitialValue() {" + NL + "      return new ApplicationStack();" + NL + "    }" + NL + "" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin input format." + NL + "   */" + NL + "  public static class InputFormat extends" + NL + "    TextVertexInputFormat<VertexId, ByteWritable, ByteWritable> {" + NL + "" + NL + "    @Override" + NL + "    public TextVertexReader createVertexReader(InputSplit split," + NL + "      TaskAttemptContext context) {" + NL + "      return new InputReader();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Henshin input reader." + NL + "     */" + NL + "    class InputReader extends" + NL + "      TextVertexReaderFromEachLineProcessedHandlingExceptions<JSONArray," + NL + "        JSONException> {" + NL + "" + NL + "      @Override" + NL + "      protected JSONArray preprocessLine(Text line) throws JSONException {" + NL + "        return new JSONArray(line.toString());" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected VertexId getId(JSONArray jsonVertex)" + NL + "        throws JSONException, IOException {" + NL + "        return jsonArrayToVertexId(jsonVertex.getJSONArray(0));" + NL + "      }" + NL + "" + NL + "      /**" + NL + "       * Convert a JSON array to a VertexId object." + NL + "       * @param jsonArray The JSON array to be converted." + NL + "       * @return The corresponding VertexId." + NL + "       */" + NL + "      private VertexId jsonArrayToVertexId(JSONArray jsonArray)" + NL + "        throws JSONException {" + NL + "        byte[] bytes = new byte[jsonArray.length()];" + NL + "        for (int i = 0; i < bytes.length; i++) {" + NL + "          bytes[i] = (byte) jsonArray.getInt(i);" + NL + "        }" + NL + "        return new VertexId(bytes);" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected ByteWritable getValue(JSONArray jsonVertex)" + NL + "        throws JSONException, IOException {" + NL + "        return new ByteWritable((byte) jsonVertex.getInt(1));" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected Iterable<Edge<VertexId, ByteWritable>> getEdges(" + NL + "        JSONArray jsonVertex) throws JSONException, IOException {" + NL + "        JSONArray jsonEdgeArray = jsonVertex.getJSONArray(2);" + NL + "        List<Edge<VertexId, ByteWritable>> edges =" + NL + "          Lists.newArrayListWithCapacity(jsonEdgeArray.length());" + NL + "        for (int i = 0; i < jsonEdgeArray.length(); ++i) {" + NL + "          JSONArray jsonEdge = jsonEdgeArray.getJSONArray(i);" + NL + "          edges.add(EdgeFactory.create(" + NL + "            jsonArrayToVertexId(jsonEdge.getJSONArray(0))," + NL + "            new ByteWritable((byte) jsonEdge.getInt(1))));" + NL + "        }" + NL + "        return edges;" + NL + "      }" + NL + "" + NL + "      @Override" + NL + "      protected Vertex<VertexId, ByteWritable, ByteWritable>" + NL + "      handleException(Text line, JSONArray jsonVertex, JSONException e) {" + NL + "        throw new IllegalArgumentException(" + NL + "          \"Couldn't get vertex from line \" + line, e);" + NL + "      }" + NL + "    }" + NL + "  }" + NL + "" + NL + "  /**" + NL + "   * Henshin output format." + NL + "   */" + NL + "  public static class OutputFormat extends" + NL + "    TextVertexOutputFormat<VertexId, ByteWritable, ByteWritable> {" + NL + "" + NL + "    @Override" + NL + "    public TextVertexWriter createVertexWriter(TaskAttemptContext context)" + NL + "      throws IOException, InterruptedException {" + NL + "      return new OutputWriter();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * Henshin output writer." + NL + "     */" + NL + "    class OutputWriter extends TextVertexWriterToEachLine {" + NL + "" + NL + "      @Override" + NL + "      protected Text convertVertexToLine(" + NL + "        Vertex<VertexId, ByteWritable, ByteWritable> vertex)" + NL + "        throws IOException {" + NL + "" + NL + "        JSONArray vertexArray = new JSONArray();" + NL + "        JSONArray idArray = new JSONArray();" + NL + "        byte[] id = vertex.getId().getBytes();" + NL + "        for (int i = 0; i < id.length; i++) {" + NL + "          idArray.put(id[i]);" + NL + "        }" + NL + "        vertexArray.put(idArray);" + NL + "        vertexArray.put(vertex.getValue().get());" + NL + "        JSONArray allEdgesArray = new JSONArray();" + NL + "        for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {" + NL + "          JSONArray edgeArray = new JSONArray();" + NL + "          JSONArray targetIdArray = new JSONArray();" + NL + "          byte[] targetId = edge.getTargetVertexId().getBytes();" + NL + "          for (int i = 0; i < targetId.length; i++) {" + NL + "            targetIdArray.put(targetId[i]);" + NL + "          }" + NL + "          edgeArray.put(targetIdArray);" + NL + "          edgeArray.put(edge.getValue().get());" + NL + "          allEdgesArray.put(edgeArray);" + NL + "        }" + NL + "        vertexArray.put(allEdgesArray);" + NL + "        return new Text(vertexArray.toString());" + NL + "      }" + NL + "    }" + NL + "  }" + NL + "" + NL + "}";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    

@SuppressWarnings("unchecked")
Map<String,Object> args = (Map<String,Object>) argument;

String packageName = (String) args.get("packageName");
//boolean logging = (Boolean) args.get("logging");


    stringBuffer.append(TEXT_1);
    stringBuffer.append( packageName );
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
